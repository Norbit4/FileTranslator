package pl.norbit.filetranslator.utils;

import org.springframework.stereotype.Component;
import pl.norbit.filetranslator.model.TranslateGroup;
import pl.norbit.filetranslator.model.TranslateRequest;
import pl.norbit.filetranslator.model.file.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class FileContentUtils {

    private Queue<TranslateRequest> getTranslateQueue(FileContent fileContent){
        LinkedList<TranslateRequest> translates = new LinkedList<>();
        List<FileObject> fileObjects = fileContent.getFileObjects();

        for(FileObject line : fileObjects){
            if (line instanceof FileLine fileLine) {

                ToTranslate toTranslate = fileLine.getToTranslate();
                Object defaultValue = toTranslate.getDefaultValue();

                if (defaultValue instanceof String) {
                    translates.add(new TranslateRequest(line.getId(), toTranslate));
                }
            }else {
                FileList fileList = (FileList) line;
                List<TranslateRequest> list = fileList.getToTranslate()
                        .stream()
                        .map(toTranslate -> new TranslateRequest(line.getId(), toTranslate))
                        .toList();

                translates.addAll(list);
            }
        }
        return translates;
    }

    private TranslateGroup getNextTranslateGroup(Queue<TranslateRequest> translateQueue) {
        int maxSize = 50;

        List<TranslateRequest> translateGroup = new ArrayList<>();

        while (maxSize > 0) {
            if (translateQueue.isEmpty()) {
                break;
            }

            TranslateRequest toTranslate = translateQueue.poll();

            translateGroup.add(toTranslate);
            maxSize--;
        }
        return new TranslateGroup(translateGroup);
    }

    public List<TranslateGroup> getTranslateGroups(FileContent fileContent) {
        Queue<TranslateRequest> translateQueue = getTranslateQueue(fileContent);

        List<TranslateGroup> translateGroups = new ArrayList<>();

        while (!translateQueue.isEmpty()) {
            translateGroups.add(getNextTranslateGroup(translateQueue));
        }

        return translateGroups;
    }
}
