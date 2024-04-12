package pl.norbit.filetranslator.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import pl.norbit.filetranslator.exception.FileException;
import pl.norbit.filetranslator.model.file.FileContent;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pl.norbit.filetranslator.helper.FileHelper.*;

@SpringBootTest
class FileServiceTest {
    @Mock
    private DeepLService deepLService;
    @Mock
    private YamlService yamlService;

    @InjectMocks
    private FileService underTest;

    @Test
    @DisplayName("Should return translated byte file")
    void should_return_translated_byte_file() {
        //given
        FileContent fileContent = getFileContent();
        String yamlContent = getYamlContent();
        String fileName = "test";

        MockMultipartFile mockFile = getMultiPartFile(yamlContent, fileName);

        when(yamlService.formatFileToFileContent(mockFile)).thenReturn(fileContent);
        when(yamlService.formatTranslateFileContentToByte(fileContent)).thenReturn(new byte[1]);

        //when
        byte[] bytes = underTest.translateFile(mockFile);

        //then
        assertNotNull(bytes);
        assertNotEquals(0, bytes.length);
    }

    @Test
    @DisplayName("Should throw FileException when file is empty")
    void should_throw_exception_when_file_is_empty() {
        //given
        MockMultipartFile mockFile = getMultiPartFile("", "test");

        //when
        Exception exception = assertThrows(FileException.class, () -> underTest.translateFile(mockFile));

        //then
        assertEquals("File is empty!", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw FileException when file type is empty")
    void should_throw_FileException_when_file_type_is_empty() {
        //given
        String yamlContent = getYamlContent();
        MockMultipartFile mockFile = new MockMultipartFile(
                "test",
                "test",
                "",
                yamlContent.getBytes(StandardCharsets.UTF_8));

        //when
        Exception exception = assertThrows(FileException.class, () -> underTest.translateFile(mockFile));

        //then
        assertEquals("File type is empty!", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw FileException when file type is empty")
    void should_throw_FileException_when_file_type_is_not_accepted() {
        //given
        String yamlContent = getYamlContent();
        MockMultipartFile mockFile = new MockMultipartFile(
                "test",
                "test.yml",
                "text/plain",
                yamlContent.getBytes(StandardCharsets.UTF_8));

        //when
        Exception exception = assertThrows(FileException.class, () -> underTest.translateFile(mockFile));

        //then
        assertEquals("This file type is not accepted!", exception.getMessage());
    }
}