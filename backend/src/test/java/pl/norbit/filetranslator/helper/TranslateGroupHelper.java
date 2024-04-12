package pl.norbit.filetranslator.helper;

import pl.norbit.filetranslator.model.TranslateGroup;
import pl.norbit.filetranslator.model.TranslateRequest;
import pl.norbit.filetranslator.model.file.FileContent;
import pl.norbit.filetranslator.model.file.FileObject;
import pl.norbit.filetranslator.model.file.ToTranslate;

import java.util.ArrayList;
import java.util.List;

public class TranslateGroupHelper {

    /**
     * Helper to get the list of groups to translate (max 50 lines in FileContent)
     *
     * @param fileContent The file content object.
     * @return The list groups to translate.
     */

    public static List<TranslateGroup> getTranslateGroups(FileContent fileContent) {
        List<FileObject> fileObjects = fileContent.getFileObjects();

        List<TranslateGroup> translateGroups = new ArrayList<>();

        for (FileObject fileObject : fileObjects) {
            ToTranslate toTranslate = new ToTranslate(fileObject.getDefaultValue());

            TranslateRequest translateRequest = new TranslateRequest(fileObject.getId(), toTranslate);
            TranslateGroup translateGroup = new TranslateGroup(List.of(translateRequest));

            translateGroups.add(translateGroup);
        }
        return translateGroups;
    }
}
