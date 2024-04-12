package pl.norbit.filetranslator.model.file;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@ToString
@Getter
public class FileContent {
    private final List<FileObject> fileObjects;
    private final String fileName;

    public FileContent(String fileName) {
        this.fileObjects = new ArrayList<>();
        this.fileName = fileName;
    }

    public void setTranslate(UUID id, String translate) {
        fileObjects.stream()
                .filter(fileObject -> fileObject.getId().equals(id))
                .findFirst()
                .ifPresent(fileObject -> fileObject.setTranslate(translate));
    }

    public void addFileObject(String key, Object o){
        // if o is a list of strings, then create a FileList object
        if (o instanceof List<?> && !((List<?>) o).isEmpty() && ((List<?>) o).get(0) instanceof String) {
            List<String> toTranslate = (List<String>) o;
            fileObjects.add(new FileList(key, toTranslate));
        } else {
            fileObjects.add(new FileLine(key, o));
        }
    }
}
