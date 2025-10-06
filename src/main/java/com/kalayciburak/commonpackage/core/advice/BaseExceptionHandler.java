package com.kalayciburak.commonpackage.core.advice;

import com.kalayciburak.commonpackage.core.advice.exception.EntityNotFoundException;
import com.kalayciburak.commonpackage.core.advice.exception.ResourceNotFoundException;
import com.kalayciburak.commonpackage.core.constant.Codes;
import com.kalayciburak.commonpackage.core.constant.Messages;
import com.kalayciburak.commonpackage.core.constant.Types;
import com.kalayciburak.commonpackage.core.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * <b>Global exception handler.</b>
 * <p>
 * Tüm exception'ları yakalar ve standart response formatında döner.
 * Her response için otomatik olarak traceId üretilir ve MDC'ye eklenir.
 * Hata detayları frontend'e gönderilmez, sadece Graylog'a loglanır ve traceId ile aranabilir.
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    /**
     * <b>Genel exception'lar için handler.</b>
     * <p>
     * {@code Exception} ve {@code RuntimeException} türündeki tüm exception'ları yakalar.
     * TraceId otomatik üretilir ve hata detayları Graylog'a kaydedilir.
     *
     * @param exception Yakalanan exception.
     * @return TraceId ve kullanıcıya gösterilecek mesaj içeren {@link ResponseEntity}.
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse<?>> handleGenericException(Exception exception) {
        return buildResponseEntity(new ErrorResponse<>(exception));
    }

    /**
     * <b>{@code IllegalArgumentException} için handler.</b>
     * <p>
     * Geçersiz argüman durumlarında çalışır. HTTP 400 döner.
     *
     * @param exception Yakalanan exception.
     * @return TraceId ve kullanıcıya gösterilecek mesaj içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse<?>> handleIllegalArgumentException(IllegalArgumentException exception) {
        var error = new ErrorResponse<>(
                Types.Error.ILLEGAL_ARGUMENT,
                Codes.ILLEGAL_ARGUMENT,
                Messages.Error.INVALID_ARGUMENT,
                HttpStatus.BAD_REQUEST,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * <b>{@code NoSuchElementException} için handler.</b>
     * <p>
     * Koleksiyon veya veri yapısında aranan eleman bulunamadığında çalışır. HTTP 404 döner.
     *
     * @param exception Yakalanan exception.
     * @return TraceId ve kullanıcıya gösterilecek mesaj içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse<?>> handleNoSuchElementException(NoSuchElementException exception) {
        var error = new ErrorResponse<>(
                Types.Error.NO_SUCH_ELEMENT,
                Codes.NO_SUCH_ELEMENT,
                Messages.Error.NO_SUCH_ELEMENT,
                HttpStatus.NOT_FOUND,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * <b>{@code ResourceNotFoundException} için handler.</b>
     * <p>
     * Kaynak bulunamadığında çalışır. HTTP 404 döner.
     *
     * @param exception Yakalanan exception.
     * @return TraceId ve kullanıcıya gösterilecek mesaj içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        var error = new ErrorResponse<>(
                Types.Error.RESOURCE_NOT_FOUND,
                Codes.RESOURCE_NOT_FOUND,
                Messages.Error.RESOURCE_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * <b>{@code EntityNotFoundException} için handler.</b>
     * <p>
     * Database entity bulunamadığında çalışır. HTTP 404 döner.
     *
     * @param exception Yakalanan exception.
     * @return TraceId ve kullanıcıya gösterilecek mesaj içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleEntityNotFoundException(EntityNotFoundException exception) {
        var error = new ErrorResponse<>(
                Types.Error.ENTITY_NOT_FOUND,
                Codes.ENTITY_NOT_FOUND,
                Messages.Error.ENTITY_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * <b>{@code MethodArgumentNotValidException} için handler.</b>
     * <p>
     * Validation hataları için çalışır. Field bazlı hata mesajları döner. HTTP 400 döner.
     *
     * @param exception Yakalanan exception.
     * @return TraceId ve validation hataları içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var validationErrors = extractValidationErrors(exception);
        var error = new ErrorResponse<>(
                Types.Error.VALIDATION,
                Codes.METHOD_ARGUMENT_NOT_VALID,
                validationErrors,
                HttpStatus.BAD_REQUEST,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * <b>ErrorResponse'u ResponseEntity olarak oluşturur.</b>
     * <p>
     * Hata detayları {@code @JsonIgnore} ile işaretlenmiştir ve frontend'e gönderilmez.
     * TraceId korunur, böylece Graylog'da loglarda takip edilebilir.
     *
     * @param errorResponse Oluşturulacak {@link ErrorResponse} nesnesi.
     * @return Frontend'e gönderilecek {@link ResponseEntity}.
     */
    public ResponseEntity<ErrorResponse<?>> buildResponseEntity(ErrorResponse<?> errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    /**
     * <b>Validation hatalarını Map formatında çıkarır.</b>
     * <p>
     * Field adı ve hata mesajı eşleşmesi döner.
     *
     * @param exception Validation exception.
     * @return Field bazlı hata mesajları.
     */
    private HashMap<String, String> extractValidationErrors(MethodArgumentNotValidException exception) {
        var validationErrors = new HashMap<String, String>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return validationErrors;
    }
}
