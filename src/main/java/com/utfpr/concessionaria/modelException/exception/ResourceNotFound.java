package com.utfpr.concessionaria.modelException.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND) //This is the HTTP protocol error code we will cover
public class ResourceNotFound extends RuntimeException{ //Run time exception will bring us the control to deal w/ exceptions

    public ResourceNotFound(String message){
        super(message);
    }

}