package pl.norbit.filetranslator.helper;

import org.springframework.mock.web.MockMultipartFile;
import pl.norbit.filetranslator.model.file.FileContent;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileHelper {

    public static String getYamlContent() {
        return """
                    key1: 'value'
                    key2: 'value2'
                    key3:
                        - 'value1'
                        - 'value2'
                    key4:
                        - 1
                        - 2
                    map:
                        key6: 10

               """;
    }

    public static FileContent getFileContent() {
        FileContent fileContent = new FileContent("test");
        fileContent.addFileObject("key1", "value");
        fileContent.addFileObject("key2", "value2");
        fileContent.addFileObject("key3", List.of("value1", "value2"));
        fileContent.addFileObject("key4", List.of(1, 2));
        fileContent.addFileObject("map.key6", 10);

        return fileContent;
    }

    public static MockMultipartFile getMultiPartFile(String yamlContent, String name) {
        return new MockMultipartFile(
                name,
                name + ".yml",
                "text/yaml",
                yamlContent.getBytes(StandardCharsets.UTF_8));
    }
}
