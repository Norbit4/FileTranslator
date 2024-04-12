package pl.norbit.filetranslator.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.filetranslator.model.TranslateGroup;
import pl.norbit.filetranslator.model.TranslateRequest;
import pl.norbit.filetranslator.model.file.FileContent;
import pl.norbit.filetranslator.model.file.FileObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.norbit.filetranslator.helper.FileHelper.getFileContent;

@SpringBootTest
class FileContentUtilsTest {
    @Autowired
    private FileContentUtils underTest;

    @Test
    @DisplayName("Should get groups from FileContent")
    void should_get_groups_from_FileContent() {
        //then
        FileContent fileContent = getFileContent();
        List<FileObject> fileObjects = fileContent.getFileObjects();

        FileObject fileObjectFirst = fileObjects.get(0);
        FileObject fileObjectSecond = fileObjects.get(1);

        //when
        List<TranslateGroup> actual = underTest.getTranslateGroups(fileContent);

        //then
        TranslateGroup group = actual.get(0);
        List<TranslateRequest> translateRequests = group.translateRequests();

        TranslateRequest firstRequest = translateRequests.get(0);
        TranslateRequest lastRequest = translateRequests.get(1);

        assertEquals(fileObjectFirst.getDefaultValue(), firstRequest.toTranslate().getDefault());
        assertEquals(fileObjectSecond.getDefaultValue(), lastRequest.toTranslate().getDefault());
    }

    @Test
    @DisplayName("Should get empty groups when fileContent is empty")
    void should_get_empty_groups_when_FileContent_is_empty() {
        //given
        FileContent fileContent = new FileContent("test");

        //when
        List<TranslateGroup> actual = underTest.getTranslateGroups(fileContent);

        //then
        assertEquals(0, actual.size());
    }
}