package pl.norbit.filetranslator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.norbit.filetranslator.model.FileLine;
import pl.norbit.filetranslator.model.TranslateGroup;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeepLService {
    private final WebClient webClient = WebClient.create("https://api-free.deepl.com/v2");
    private final ObjectMapper mapper = new ObjectMapper();
    private final String BODY_TEMPLATE =
            """
                {
                  "text": [%s],
                  "target_lang": "PL",
                  "source_lang": "EN"
                }
            """;

    public void translate(TranslateGroup group)  {
        List<FileLine> lines = group.getLines();

        List<String> list = new ArrayList<>();

        for (FileLine line : lines) list.add(line.getText());

        try {
            List<String> translate = translate(list);

            for (int i = 0; i < translate.size(); i++) group.addTranslate(i, translate.get(i));

//            return group;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> translate(List<String> list) throws JsonProcessingException {
        String body = String.format(BODY_TEMPLATE, formatList(list));

        String result = webClient.post()
                .uri("/translate")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "DeepL-Auth-Key b522cbab-5e9d-5e26-2056-d6c5821083e2:fx")
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

    private List<String> formatBody(String body) throws JsonProcessingException {
        JsonNode json = mapper.readTree(body);
        JsonNode translations = json.path("translations");

        List<String> textList = new ArrayList<>();

        translations.forEach(translation -> textList.add(translation.path("text").asText()));

        return textList;
    }
}
