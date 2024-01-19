package pl.norbit.filetranslator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.yaml.snakeyaml.Yaml;
import pl.norbit.filetranslator.model.FileContent;
import pl.norbit.filetranslator.model.FileLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
                                
                                key1: 'value'
                                key2: 'value'
                                key3:
                                    - 'value1'
                                    - 'value2'
                                key4:
                                    - 1
                                    - 2
                                map:
                                    key6: 10
                                                      
                             """;

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.yml",
                "text/plain",
                yamlContent.getBytes(StandardCharsets.UTF_8));

        FileContent expectedFileContent = new FileContent();
        expectedFileContent.addLine(new FileLine("key1", "value"));
        expectedFileContent.addLine(new FileLine("key2", "value"));
        expectedFileContent.addLine(new FileLine("key3", List.of("value1", "value2")));
        expectedFileContent.addLine(new FileLine("key4", List.of(1, 2)));
        expectedFileContent.addLine(new FileLine("map.key6", 10));

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
        FileLine line3 = new FileLine("key3", 1);
        FileLine line4 = new FileLine("key4", List.of("line1", "line2"));
        FileLine line5 = new FileLine("key5", List.of(1, 2));
        FileLine line6 = new FileLine("map.key6", "text");

        line1.setTranslate("translate1");
        line2.setTranslate("translate2");
        line4.setTranslate("[line1, line2]");
        line6.setTranslate("text");

        fileContent.addLine(line1);
        fileContent.addLine(line2);
        fileContent.addLine(line3);
        fileContent.addLine(line4);
        fileContent.addLine(line5);
        fileContent.addLine(line6);

        Map<String, Object> expectedMap = new HashMap<>();
        Map<Object, Object> lineMap = new LinkedHashMap<>();
        lineMap.put("key6","text");

        expectedMap.put("key1", "translate1");
        expectedMap.put("key2", "translate2");
        expectedMap.put("key3", 1);
        expectedMap.put("key4", List.of("line1", "line2"));
        expectedMap.put("key5",  List.of(1, 2));
        expectedMap.put("map", lineMap);

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