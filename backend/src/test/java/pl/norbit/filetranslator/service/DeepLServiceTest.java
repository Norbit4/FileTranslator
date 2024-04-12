package pl.norbit.filetranslator.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.filetranslator.model.TranslateGroup;
import pl.norbit.filetranslator.model.file.FileContent;
import pl.norbit.filetranslator.model.file.FileObject;
import pl.norbit.filetranslator.utils.FileContentUtils;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static pl.norbit.filetranslator.helper.TranslateGroupHelper.getTranslateGroups;

@SpringBootTest
class DeepLServiceTest {
    @Mock
    private FileContentUtils fileContentUtils;

    @Mock
    private Dotenv dotenv;

    @Test
    @DisplayName("Should success initialize")
    void should_success_initialize() {
        when(dotenv.get("DEEPL_URL")).thenReturn("https://api-free.deepl.com/v2");
        when(dotenv.get("DEEPL_ACCESS_TOKEN")).thenReturn("your_access_token");

        assertDoesNotThrow(() -> new DeepLService(fileContentUtils, dotenv));
    }

    @Test
    @DisplayName("Should throw exception when DEEPL_URL is not set")
    void should_throw_exception_when_DEEPL_URL_is_not_set() {
        when(dotenv.get("DEEPL_URL")).thenReturn(null);
        when(dotenv.get("DEEPL_ACCESS_TOKEN")).thenReturn("your_access_token");

        assertThrows(IllegalArgumentException.class, () -> new DeepLService(fileContentUtils, dotenv));
    }

    @Test
    @DisplayName("Should throw exception when DEEPL_ACCESS_TOKEN is not set")
    void should_throw_exception_when_DEEPL_ACCESS_TOKEN_is_not_set() {
        when(dotenv.get("DEEPL_URL")).thenReturn("https://api-free.deepl.com/v2");
        when(dotenv.get("DEEPL_ACCESS_TOKEN")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> new DeepLService(fileContentUtils, dotenv));
    }

    @Test
    @Disabled
    @DisplayName("Should translate FileContent")
    void should_translate_FileContent() {
        //given
        String deeplUrl = "https://api-free.deepl.com/v2";
        String deeplAccessToken = "your_access_token"; //replace with your access token and remove @Disabled annotation

        FileContent fileContent = new FileContent("animals");

        fileContent.addFileObject("key1", "dog");
        fileContent.addFileObject("key2", "cat");

        List<TranslateGroup> translateGroups = getTranslateGroups(fileContent);

        when(fileContentUtils.getTranslateGroups(fileContent)).thenReturn(translateGroups);
        when(dotenv.get("DEEPL_URL")).thenReturn(deeplUrl);
        when(dotenv.get("DEEPL_ACCESS_TOKEN")).thenReturn(deeplAccessToken);

        DeepLService deepLService = new DeepLService(fileContentUtils, dotenv);

        //when
        deepLService.translate(fileContent);

        //then
        List<FileObject> actualObjects = fileContent.getFileObjects();

        assertEquals(2, actualObjects.size());
        assertEquals("pies", actualObjects.get(0).getTranslateValue());
        assertEquals("kot", actualObjects.get(1).getTranslateValue());
    }
}