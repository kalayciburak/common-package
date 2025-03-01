package com.kalayciburak.commonpackage.core.advice;

import com.kalayciburak.commonpackage.core.advice.exception.EntityNotFoundException;
import com.kalayciburak.commonpackage.core.advice.exception.ResourceNotFoundException;
import com.kalayciburak.commonpackage.core.constant.Codes;
import com.kalayciburak.commonpackage.core.constant.Messages;
import com.kalayciburak.commonpackage.core.constant.Profiles;
import com.kalayciburak.commonpackage.core.constant.Types;
import com.kalayciburak.commonpackage.core.response.error.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class BaseExceptionHandler {
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    /**
     * <b>Genel istisnalar için işleyici tanımlar.</b>
     * <p>
     * Bu işleyici, {@code Exception} ve {@code RuntimeException} türündeki istisnaları ve alt türlerini yakalar. İşlenmemiş
     * genel hataları ele alarak sistem genelinde tutarlı bir hata yönetimi stratejisi sağlar. Bu yöntem, uygulamanın diğer
     * bölümlerinde ayrı ayrı hata yönetimi gereksinimini azaltır.
     *
     * @param exception yakalanacak istisna.
     * @return Oluşturulan hata ile ilgili bilgileri içeren {@link ResponseEntity}.
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse<?>> handleGenericException(Exception exception) {
        return buildResponseEntity(new ErrorResponse<>(exception));
    }

    /**
     * {@code IllegalArgumentException} türündeki istisnalar için özel bir işleyici tanımlar.
     * <p>
     * Bu tür istisnalar, metodlara geçersiz argümanlar gönderildiğinde ortaya çıkar. İstisna bilgileri, kullanıcıya geçersiz
     * girişin neden olduğu hata hakkında bilgi vermek için kullanılır.
     *
     * @param exception yakalanacak istisna.
     * @return Oluşturulan hata ile ilgili bilgileri içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse<?>> handleIllegalArgumentException(IllegalArgumentException exception) {
        var error = new ErrorResponse<>(
                Types.Exception.ILLEGAL_ARGUMENT,
                Codes.ILLEGAL_ARGUMENT,
                Messages.Error.INVALID_ARGUMENT,
                HttpStatus.BAD_REQUEST,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * {@code NoSuchElementException} türündeki istisnalar için özel bir işleyici tanımlar.
     * <p>
     * Bu tür istisnalar, bir koleksiyonun veya veri yapısının içinde aranan elemanın bulunamadığında ortaya çıkar.
     *
     * @param exception yakalanacak istisna.
     * @return Oluşturulan hata ile ilgili bilgileri içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse<?>> handleNoSuchElementException(NoSuchElementException exception) {
        var error = new ErrorResponse<>(
                Types.Exception.NO_SUCH_ELEMENT,
                Codes.NO_SUCH_ELEMENT,
                Messages.Error.NO_SUCH_ELEMENT,
                HttpStatus.NOT_FOUND,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * {@code ResourceNotFoundException} türündeki istisnalar için özel bir işleyici tanımlar.
     * <p>
     * Bu tür istisnalar, bir kaynağın bulunamadığında ortaya çıkar.
     *
     * @param exception yakalanacak istisna.
     * @return Oluşturulan hata ile ilgili bilgileri içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        var error = new ErrorResponse<>(
                Types.Exception.RESOURCE_NOT_FOUND,
                Codes.RESOURCE_NOT_FOUND,
                Messages.Error.RESOURCE_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * Custom {@code EntityNotFoundException} türündeki istisnalar için özel bir işleyici tanımlar.
     * <p>
     * Bu tür istisnalar, bir varlığın bulunamadığında ortaya çıkar.
     *
     * @param exception yakalanacak istisna.
     * @return Oluşturulan hata ile ilgili bilgileri içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleEntityNotFoundException(EntityNotFoundException exception) {
        var error = new ErrorResponse<>(
                Types.Exception.ENTITY_NOT_FOUND,
                Codes.ENTITY_NOT_FOUND,
                Messages.Error.ENTITY_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                exception);

        return buildResponseEntity(error);
    }

    /**
     * {@code MethodArgumentNotValidException} istisnasını yakalar ve bu türdeki doğrulama hatalarını işler.
     * <p>
     * Bu işleyici, genellikle Spring MVC'nin otomatik doğrulama mekanizmaları tarafından fırlatılır ve kullanıcıya yapılan
     * girişlerin neden geçersiz olduğuna dair ayrıntılı bilgiler sağlar.
     *
     * @param exception yakalanacak istisna.
     * @return Doğrulama hatalarını içeren {@link ResponseEntity}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var error = new ErrorResponse<>(
                Types.Exception.VALIDATION,
                Codes.METHOD_ARGUMENT_NOT_VALID,
                Messages.Error.VALIDATION_ERROR,
                HttpStatus.BAD_REQUEST,
                exception);
        populateValidationErrors(exception, error);

        return buildResponseEntity(error);
    }

    /**
     * <b>Hata nesnesini {@code ResponseEntity} olarak oluşturur.</b>
     *
     * @param errorResponse oluşturulacak {@link ErrorResponse} nesnesi.
     * @return Hata durumuna uygun {@link ResponseEntity} döner.
     */
    public ResponseEntity<ErrorResponse<?>> buildResponseEntity(ErrorResponse<?> errorResponse) {
        if (isProdLikeProfile()) return new ResponseEntity<>(new ErrorResponse<>(errorResponse), errorResponse.getStatus());

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    /**
     * <b>Validation hatalarını {@code BaseError} nesnesine ekler.</b>
     *
     * @param exception     içerisinde validasyon hataları barındıran istisna.
     * @param errorResponse hataların ekleneceği {@link ErrorResponse} nesnesi.
     */
    private static void populateValidationErrors(MethodArgumentNotValidException exception, ErrorResponse<String> errorResponse) {
        var validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        errorResponse.getDetail().setDebugMessage(Messages.Error.VALIDATION_ERROR);
        errorResponse.setMessage(validationErrors);
    }

    /**
     * <b>Aktif profilin prod benzeri bir profil olup olmadığını kontrol eder.</b>
     *
     * @return Aktif profil prod benzeri bir profil ise {@code true} döner.
     */
    private boolean isProdLikeProfile() {
        return Profiles.PRODUCTION.contains(activeProfile);
    }
}
