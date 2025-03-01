package com.kalayciburak.commonpackage.core.advice.exception;

/**
 * Herhangi bir kaynak bulunamadığında fırlatılacak exception sınıfı.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

