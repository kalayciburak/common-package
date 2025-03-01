package com.kalayciburak.commonpackage.core.advice.exception;

/**
 * Herhangi bir entity bulunamadığında fırlatılacak exception sınıfı.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
