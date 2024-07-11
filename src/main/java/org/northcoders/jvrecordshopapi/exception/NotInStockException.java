package org.northcoders.jvrecordshopapi.exception;

public class NotInStockException extends RuntimeException{
    public NotInStockException(String message) {
        super(message);
    }
}
