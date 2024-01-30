package com.testtask.testtask.api.exeption;

import lombok.AllArgsConstructor;
import lombok.Generated;
@Generated
public class ValidationException extends IllegalArgumentException{
    public ValidationException(String message){
        super(message);
    }
}
