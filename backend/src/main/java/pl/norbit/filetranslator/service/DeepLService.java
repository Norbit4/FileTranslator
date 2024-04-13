package pl.norbit.filetranslator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.norbit.filetranslator.exception.JsonException;
import pl.norbit.filetranslator.model.Language;
import pl.norbit.filetranslator.model.TranslateGroup;
import pl.norbit.filetranslator.model.TranslateRequest;
import pl.norbit.filetranslator.model.file.FileContent;
import pl.norbit.filetranslator.model.file.ToTranslate;
import pl.norbit.filetranslator.utils.FileContentUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DeepLService {
    private final WebClient webClient;
    private final ObjectMapper mapper;

    private final FileContentUtils fileUtils;

    private final String deeplAccessToken;

    public DeepLService(FileContentUtils fileUtils, Dotenv dotenv) {
        this.fileUtils = fileUtils;

        String deeplUrl = dotenv.get("DEEPL_URL");
        this.deeplAccessToken = dotenv.get("DEEPL_ACCESS_TOKEN");

        if (deeplUrl == null || deeplAccessToken == null) {
            throw new IllegalArgumentException("DEEPL_URL or DEEPL_ACCESS_TOKEN is not set");
        }

        this.mapper = new ObjectMapper();
        this.webClient = WebClient.create(deeplUrl);
    }

    public void translate(FileContent fileContent, Language targetLanguage) {
        List<TranslateGroup> groups = fileUtils.getTranslateGroups(fileContent);

        for (TranslateGroup translateGroup : groups) {

            List<TranslateRequest> translateRequests = translateGroup.translateRequests();

            List<String> list = translateRequests.stream()
                    .map(TranslateRequest::toTranslate)
                    .map(ToTranslate::getDefault)
                    .toList();

            List<String> translate = translate(list, targetLanguage);

            for (int i = 0; i < translate.size(); i++) {
                UUID id = translateRequests.get(i).id();
                fileContent.setTranslate(id, translate.get(i));
            }
        }
    }

    private List<String> translate(List<String> list, Language targetLanguage) {
        String template = """
                    {
                      "text": [%s],
                      "target_lang": "%s"
                    }
                """;

        String body = String.format(template, formatList(list), targetLanguage.name());
        String authorization = "DeepL-Auth-Key " + deeplAccessToken;

        String result = webClient.post()
                .uri("/translate")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", authorization)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return formatBody(result);
    }

    private StringBuilder formatList(List<String> list) {
        StringBuilder builder = new StringBuilder();

        list.forEach(text -> builder.append("\"").append(text).append("\","));

        builder.deleteCharAt(builder.length() - 1);

        return builder;
    }

    private List<String> formatBody(String body)  {
        JsonNode json;
        try {
            json = mapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new JsonException("Error while parsing json");
        }
        JsonNode translations = json.path("translations");

        List<String> textList = new ArrayList<>();

        translations.forEach(translation -> textList.add(translation.path("text").asText()));

        return textList;
    }
}
