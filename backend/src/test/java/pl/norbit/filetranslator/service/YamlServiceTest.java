package pl.norbit.filetranslator.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import pl.norbit.filetranslator.model.file.FileContent;
import pl.norbit.filetranslator.model.file.FileObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.norbit.filetranslator.helper.FileHelper.*;

@SpringBootTest
class YamlServiceTest {
    @Autowired
    private YamlService underTest;

    @Test
    @DisplayName("Should format file to FileContent")
    void should_format_file_to_FileContent() {
        //given
        String yamlContent = getYamlContent();
        String fileName = "test";

        MockMultipartFile mockFile = getMultiPartFile(yamlContent, fileName);
        FileContent expectedFileContent = getFileContent();

        //when
        FileContent actual = underTest.formatFileToFileContent(mockFile);

        //then
        assertNotNull(actual);
        assertEquals(expectedFileContent.getFileName(), actual.getFileName());

        List<FileObject> expectedFileObjects = expectedFileContent.getFileObjects();
        List<FileObject> actualFileObjects = actual.getFileObjects();

        assertEquals(expectedFileObjects.size(), actualFileObjects.size());

        for (int i = 0; i < expectedFileObjects.size(); i++) {
            FileObject expectedFileObject = expectedFileObjects.get(i);
            FileObject actualFileObject = actualFileObjects.get(i);

            assertEquals(expectedFileObject.getKey(), actualFileObject.getKey());
            assertEquals(expectedFileObject.getDefaultValue(), actualFileObject.getDefaultValue());
        }
    }

    @Test
    @DisplayName("Should format FileContent to byte")
    void should_format_FileContent_to_byte() {
        //given
        FileContent fileContent = getFileContent();

        //when
        byte[] actual = underTest.formatTranslateFileContentToByte(fileContent);

        //then
        assertNotNull(actual);
        assertNotEquals(0, actual.length);
    }
}