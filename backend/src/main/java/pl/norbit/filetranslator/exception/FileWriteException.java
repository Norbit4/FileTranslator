package pl.norbit.filetranslator.exception;

public class FileWriteException extends RuntimeException{

    public FileWriteException (String message){
        super(message);
    }
    public FileWriteException(String message, Throwable throwable){
        super(message, throwable);
    }
}
