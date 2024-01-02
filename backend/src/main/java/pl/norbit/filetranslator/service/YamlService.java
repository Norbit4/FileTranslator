package pl.norbit.filetranslator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import pl.norbit.filetranslator.model.FileContent;
import pl.norbit.filetranslator.model.FileLine;
import pl.norbit.filetranslator.model.TranslateGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class YamlService {

    public FileContent getContent(MultipartFile file) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.load(file.getInputStream());

        return setupContent(map, "", new FileContent());
    }

    @SuppressWarnings("unchecked")
    public static FileContent setupContent(Map<String, Object> map, String path, FileContent fileContent) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String keyPath = path.isEmpty() ? key : path + "." + key;

            if (value instanceof Map) {
                setupContent((Map<String, Object>) value, keyPath, fileContent);
            }else {
                fileContent.addLine(new FileLine(keyPath, value.toString()));
            }
        }
        return fileContent;
    }

    public byte [] translateFile(List<TranslateGroup> groups) throws IOException {
        Map<String, Object> map = new LinkedHashMap<>();

        for (TranslateGroup group : groups) {
            List<FileLine> lines = group.getLines();

            for (FileLine line : lines) {
                String[] split = line.getKey().split("\\.");

                if(split.length != 1) {
                    HashMap<Object, Object> objectObjectHashMap = new LinkedHashMap<>();

                    objectObjectHashMap.put(split[1], line.getTranslate());

                    map.put(split[0], objectObjectHashMap);
                }else {
                    map.put(line.getKey(), line.getTranslate());
                }

//
//                map.put(line.getKey(), line.getTranslate());
            }
        }

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        options.setPrettyFlow(true);

        Yaml newYaml = new Yaml(options);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Writer writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8)) {
            newYaml.dump(map, writer);
            return byteArrayOutputStream.toByteArray();
        }
    }
}
