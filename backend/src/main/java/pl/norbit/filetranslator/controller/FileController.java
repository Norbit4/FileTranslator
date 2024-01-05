package pl.norbit.filetranslator.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.norbit.filetranslator.model.TranslateInfo;
import pl.norbit.filetranslator.service.FileService;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> Upload(@RequestParam("file") MultipartFile file){

        if(file == null || file.isEmpty()) return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);

        String contentType = file.getContentType();

        if(contentType == null || contentType.isEmpty()) return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);

        if(!contentType.equals("text/yaml")) return new ResponseEntity<>("File is not accepted", HttpStatus.BAD_REQUEST);

        TranslateInfo translateInfo = fileService.translateFile(file);

        if(translateInfo.getStatus() == null) return new ResponseEntity<>("Error while translating", HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file.yml\"")
                .body(translateInfo.getFile());
    }
}
