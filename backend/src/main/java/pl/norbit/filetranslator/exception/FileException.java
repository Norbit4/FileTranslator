package pl.norbit.filetranslator.exception;


public class FileException extends RuntimeException{

    public FileException(String message){
        super(message);
    }
    public FileException(String message, Throwable throwable){
        super(message, throwable);
    }
}
