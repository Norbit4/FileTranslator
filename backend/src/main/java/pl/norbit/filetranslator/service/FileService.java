package pl.norbit.filetranslator.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.norbit.filetranslator.model.FileContent;
import pl.norbit.filetranslator.model.TranslateInfo;
import pl.norbit.filetranslator.enums.TranslateStatus;

import java.io.*;

@Service
@AllArgsConstructor
public class FileService {
    private DeepLService deepLService;
    private YamlService yamlService;

    public TranslateInfo translateFile(MultipartFile file) {

        if(file == null || file.isEmpty()) return new TranslateInfo(TranslateStatus.FILE_IS_EMPTY, null);

        String contentType = file.getContentType();

        if(contentType == null || contentType.isEmpty()) return new TranslateInfo(TranslateStatus.FILE_IS_EMPTY, null);

        if(!contentType.equals("text/yaml")) return new TranslateInfo(TranslateStatus.NOT_ACCEPTED, null);

        try {
            FileContent fileContent = yamlService.formatFileToFileContent(file);

            deepLService.translate(fileContent);

            byte[] content = yamlService.formatFileContentToByte(fileContent);

            return new TranslateInfo(TranslateStatus.SUCCESS, content);
        } catch (IOException e) {
            return new TranslateInfo(TranslateStatus.ERROR, null);
        }
    }
}
