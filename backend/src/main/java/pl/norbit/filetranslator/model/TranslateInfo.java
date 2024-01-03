package pl.norbit.filetranslator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.norbit.filetranslator.enums.TranslateStatus;

@Data
@AllArgsConstructor
public class TranslateInfo {

    private TranslateStatus status;
    private byte[] file;
}
