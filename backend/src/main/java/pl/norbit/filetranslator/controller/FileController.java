package pl.norbit.filetranslator.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.norbit.filetranslator.service.FileService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> Upload(@RequestParam("file") MultipartFile file){
        byte[] body = fileService.translateFile(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file.yml\"")
                .body(body);
    }
}
