package pl.norbit.filetranslator.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TranslateStatus {

    SUCCESS("", HttpStatus.OK),
    ERROR("Error while translating", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_IS_EMPTY("File is empty", HttpStatus.BAD_REQUEST),
    NOT_ACCEPTED("File is not accepted",  HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

    TranslateStatus(String message,HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
