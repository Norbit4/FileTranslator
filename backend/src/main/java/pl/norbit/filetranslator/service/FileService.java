package pl.norbit.filetranslator.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.norbit.filetranslator.exception.FileException;
import pl.norbit.filetranslator.model.file.FileContent;

@Service
@AllArgsConstructor
public class FileService {
    private final DeepLService deepLService;
    private final YamlService yamlService;

    public byte[] translateFile(MultipartFile file) {

        if(file == null || file.isEmpty()) throw new FileException("File is empty!");

        String contentType = file.getContentType();

        if(contentType == null || contentType.isEmpty()) throw new FileException("File type is empty!");

        if(!contentType.equals("text/yaml")) throw new FileException("This file type is not accepted!");

        FileContent fileContent = yamlService.formatFileToFileContent(file);

        deepLService.translate(fileContent);

        return yamlService.formatTranslateFileContentToByte(fileContent);
    }
}
