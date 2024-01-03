package pl.norbit.filetranslator.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.norbit.filetranslator.model.FileContent;
import pl.norbit.filetranslator.model.TranslateGroup;
import pl.norbit.filetranslator.model.TranslateInfo;
import pl.norbit.filetranslator.enums.TranslateStatus;

import java.io.*;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private DeepLService deepLService;
    private YamlService yamlService;

    public TranslateInfo translateFile(MultipartFile file) {
        try {
            FileContent fileContent = yamlService.getContent(file);

            List<TranslateGroup> groups = fileContent.getGroups();

            for (TranslateGroup group : groups) deepLService.translate(group);

            byte[] content = yamlService.translateFile(groups);

            return new TranslateInfo(TranslateStatus.SUCCESS, content);
        } catch (IOException e) {
            return new TranslateInfo(TranslateStatus.ERROR, null);
        }
    }
}
