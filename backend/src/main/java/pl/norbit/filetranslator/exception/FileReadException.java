package pl.norbit.filetranslator.exception;

public class FileReadException extends RuntimeException{

    public FileReadException(String message){
        super(message);
    }
    public FileReadException(String message, Throwable throwable){
        super(message, throwable);
    }
}
