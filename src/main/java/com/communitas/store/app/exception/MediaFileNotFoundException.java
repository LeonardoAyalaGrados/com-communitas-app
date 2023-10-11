package com.communitas.store.app.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MediaFileNotFoundException extends RuntimeException{

    public  MediaFileNotFoundException (String message){
        super(message);
    }
    public MediaFileNotFoundException(String message,Throwable ex){
        super(message,ex);
    }
}
