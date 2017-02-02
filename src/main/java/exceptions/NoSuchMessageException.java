package exceptions;

import java.util.NoSuchElementException;

public class NoSuchMessageException extends Exception{
    public NoSuchMessageException(String message){
        super(message);
    }
}
