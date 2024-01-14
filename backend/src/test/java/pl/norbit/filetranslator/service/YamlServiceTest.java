package pl.norbit.filetranslator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.yaml.snakeyaml.Yaml;
import pl.norbit.filetranslator.model.FileContent;
import pl.norbit.filetranslator.model.FileLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class YamlServiceTest {

    private YamlService onTest;

    @BeforeEach
    void setUp() {
        onTest = new YamlService();
    }

    @Test
    void itShouldFormatFileToFileContent() {
        //given
        String yamlContent = """
                                
                                key1: value
                                key2: value
                                                      
                             """;

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.yml",
                "text/plain",
                yamlContent.getBytes(StandardCharsets.UTF_8));

        FileContent expectedFileContent = new FileContent();
        expectedFileContent.addLine(new FileLine("key1", "value"));
        expectedFileContent.addLine(new FileLine("key2", "value"));

        //when
        FileContent result = onTest.formatFileToFileContent(mockFile);

        //then
        assertEquals(expectedFileContent, result);
    }


    @Test
    void itShouldFormatTranslateFileContentToByte() throws IOException {
        //given
        FileContent fileContent = new FileContent();

        FileLine line1 = new FileLine("key1", "text");
        FileLine line2 = new FileLine("key2", "text");

        line1.setTranslate("translate1");
        line2.setTranslate("translate2");

        fileContent.addLine(line1);
        fileContent.addLine(line2);

        Map<String, Object> expectedMap = new HashMap<>();

        expectedMap.put("key1", "translate1");
        expectedMap.put("key2", "translate2");

        //when
        byte[] result = onTest.formatTranslateFileContentToByte(fileContent);

        Yaml yaml = new Yaml();
        Map<String, Object> resultMap;

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(result)) {
            resultMap = yaml.load(inputStream);
        }

        //then
        assertEquals(expectedMap, resultMap);
    }
}