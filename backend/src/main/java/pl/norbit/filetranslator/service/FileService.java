package pl.norbit.filetranslator.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.norbit.filetranslator.model.FileContent;
import pl.norbit.filetranslator.model.TranslateGroup;

import java.io.*;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private DeepLService deepLService;
    private YamlService yamlService;

    public byte [] translateFile(MultipartFile file) {
        try {
            FileContent fileContent = yamlService.getContent(file);

            List<TranslateGroup> groups = fileContent.getGroups();

            groups.forEach(group -> deepLService.translate(group));

            return yamlService.translateFile(groups);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
