package pl.norbit.filetranslator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import pl.norbit.filetranslator.exception.FileReadException;
import pl.norbit.filetranslator.exception.FileWriteException;
import pl.norbit.filetranslator.model.file.FileContent;
import pl.norbit.filetranslator.model.file.FileObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class YamlService {

    public FileContent formatFileToFileContent(MultipartFile file)  {
        Yaml yaml = new Yaml();
        Map<String, Object> map;
        try {
            map = yaml.load(file.getInputStream());
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }

        return setupContent(map, "", new FileContent(file.getName()));
    }
    @SuppressWarnings("unchecked")
    public static FileContent setupContent(Map<String, Object> map, String path, FileContent fileContent) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            String keyPath = path.isEmpty() ? key : path + "." + key;

            if (value instanceof Map) {
                setupContent((Map<String, Object>) value, keyPath, fileContent);
            } else {
                fileContent.addFileObject(keyPath, value);
            }
        }
        return fileContent;
    }

    public byte[] formatTranslateFileContentToByte(FileContent fileContent)  {
        List<FileObject> fileObjects = fileContent.getFileObjects();

        Map<String, Object> map = getFileMap(fileObjects);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setAllowUnicode(true);
        options.setPrettyFlow(true);

        Yaml newYaml = new Yaml(options);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Writer writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8)) {
            newYaml.dump(map, writer);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new FileWriteException(e.getMessage());
        }
    }

    private Map<String, Object> getFileMap(List<FileObject> groups) {
        Map<String, Object> map = new LinkedHashMap<>();

        groups.forEach(fileLine -> mapLine(map, fileLine));

        return map;
    }

    @SuppressWarnings("unchecked")
    private void mapLine(Map<String, Object> map, FileObject line) {
        String[] split = line.getKey().split("\\.");

        Map<String, Object> currentMap = map;

        for (int i = 0; i < split.length - 1; i++) {
            String key = split[i];
            currentMap = (Map<String, Object>) currentMap.computeIfAbsent(key, k -> new LinkedHashMap<>());
        }

        String lastKey = split[split.length - 1];
        currentMap.put(lastKey, line.getTranslateValue());
    }
}