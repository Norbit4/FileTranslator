package pl.norbit.filetranslator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = FileException.class)
    public ResponseEntity<ApiException> handleFileException(FileException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(),status,
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status );
    }

    @ExceptionHandler(value = FileReadException.class)
    public ResponseEntity<ApiException> handleFileReadException(FileReadException e){
        HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        ApiException apiException = new ApiException(e.getMessage(), status,
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status);
    }
    @ExceptionHandler(value = FileWriteException.class)
    public ResponseEntity<ApiException> handleFileWriteException(FileWriteException e){
        HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        ApiException apiException = new ApiException(e.getMessage(), status,
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(value = JsonException.class)
    public ResponseEntity<ApiException> handleJsonException(JsonException e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(e.getMessage(), status,
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiException> handleOther(RuntimeException e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(e.getMessage(), status,
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(apiException, status);
    }
}
