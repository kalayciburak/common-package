package com.kalayciburak.commonpackage.aspect.logging;

import com.kalayciburak.commonpackage.core.response.error.ErrorResponse;
import com.kalayciburak.commonpackage.core.util.TraceIdGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.kalayciburak.commonpackage.core.constant.Messages.LogTemplate.*;

/**
 * <b>Exception handler metodlarını loglayan Aspect.</b>
 * <p>
 * Her hata için traceId ile birlikte structured logging yapar.
 * Hata detayları Graylog'a gönderilir ve traceId üzerinden takip edilebilir.
 * Frontend'e sadece kullanıcıya gösterilecek mesaj ve traceId döner.
 */
@Aspect
@Component
public class ExceptionLoggingAspect {
    private static final String LOG_TYPE = "error_log";
    private static final Logger log = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    /**
     * <b>Exception handler çalışmadan önce MDC'ye log type ekler.</b>
     */
    @Before("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void beforeAdvice() {
        MDC.put("log_type", LOG_TYPE);
    }

    /**
     * <b>Exception handler başarıyla çalıştıktan sonra detaylı loglama yapar.</b>
     * <p>
     * TraceId ile birlikte hata detaylarını Graylog'a gönderir.
     * ErrorDetail bilgileri structured logging ile kaydedilir.
     *
     * @param joinPoint Exception handler join point.
     * @param response  Handler'dan dönen response.
     */
    @AfterReturning(value = "@annotation(org.springframework.web.bind.annotation.ExceptionHandler)", returning = "response")
    public void logAfterReturning(JoinPoint joinPoint, Object response) {
        try {
            var handlerInfo = createHandlerInfo(joinPoint);
            var traceId = TraceIdGenerator.get();

            if (isErrorResponse(response)) logErrorResponse(handlerInfo, traceId, response);
            else logUnexpectedResponse(handlerInfo, traceId, response);
        } finally {
            MDC.clear();
        }
    }

    /**
     * <b>Exception handler içinde beklenmeyen hata oluşursa loglar.</b>
     *
     * @param joinPoint Exception handler join point.
     * @param exception Oluşan exception.
     */
    @AfterThrowing(pointcut = "@annotation(org.springframework.web.bind.annotation.ExceptionHandler)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        try {
            var handlerInfo = createHandlerInfo(joinPoint);
            var traceId = TraceIdGenerator.get();

            log.error(UNEXPECTED_ERROR, handlerInfo, traceId, exception);
        } finally {
            MDC.clear();
        }
    }

    /**
     * <b>ErrorResponse tipindeki response'ları loglar.</b>
     *
     * @param handlerInfo Handler bilgisi.
     * @param traceId     Request traceId'si.
     * @param response    Handler'dan dönen response.
     */
    private void logErrorResponse(String handlerInfo, String traceId, Object response) {
        var errorResponse = extractErrorResponse(response);
        if (errorResponse != null) {
            enrichMdcWithErrorInfo(errorResponse);
            logError(handlerInfo, traceId, errorResponse);
        } else logUnexpectedResponse(handlerInfo, traceId, response);
    }

    /**
     * <b>Beklenmeyen response tiplerini loglar.</b>
     *
     * @param handlerInfo Handler bilgisi.
     * @param traceId     Request traceId'si.
     * @param response    Handler'dan dönen response.
     */
    private void logUnexpectedResponse(String handlerInfo, String traceId, Object response) {
        log.error(WITH_RESPONSE, handlerInfo, traceId, response);
    }

    /**
     * <b>MDC'ye error bilgilerini ekler.</b>
     *
     * @param errorResponse ErrorResponse nesnesi.
     */
    private void enrichMdcWithErrorInfo(ErrorResponse<?> errorResponse) {
        MDC.put("error_type", errorResponse.getType());
        MDC.put("error_code", errorResponse.getCode());
        MDC.put("http_status", String.valueOf(errorResponse.getStatus().value()));
        if (errorResponse.getDetail() != null) enrichMdcWithErrorDetail(errorResponse);
    }

    /**
     * <b>MDC'ye detaylı error bilgilerini ekler.</b>
     *
     * @param errorResponse ErrorResponse nesnesi.
     */
    private void enrichMdcWithErrorDetail(ErrorResponse<?> errorResponse) {
        var detail = errorResponse.getDetail();
        MDC.put("error_class", detail.getClassName());
        MDC.put("error_method", detail.getMethodName());
        MDC.put("error_line", String.valueOf(detail.getLineNumber()));
        MDC.put("exception_type", detail.getExceptionType());
    }

    /**
     * <b>Hata logunu yazar.</b>
     *
     * @param handlerInfo   Handler bilgisi.
     * @param traceId       Request traceId'si.
     * @param errorResponse ErrorResponse nesnesi.
     */
    private void logError(String handlerInfo, String traceId, ErrorResponse<?> errorResponse) {
        var message = errorResponse.getMessage();
        var detail = errorResponse.getDetail();

        if (detail != null) log.error(WITH_MESSAGE_AND_DETAIL, handlerInfo, traceId, message, detail);
        else log.error(WITH_MESSAGE, handlerInfo, traceId, message);
    }

    /**
     * <b>Handler bilgisini formatlar.</b>
     *
     * @param joinPoint Join point.
     * @return Formatlanmış handler bilgisi.
     */
    private String createHandlerInfo(JoinPoint joinPoint) {
        var className = joinPoint.getTarget().getClass().getSimpleName();
        var methodName = joinPoint.getSignature().getName();

        return String.format("%s.%s", className, methodName);
    }

    /**
     * <b>Response'un ErrorResponse tipinde olup olmadığını kontrol eder.</b>
     *
     * @param response Kontrol edilecek response.
     * @return ErrorResponse ise true, değilse false.
     */
    private boolean isErrorResponse(Object response) {
        return response instanceof ResponseEntity<?> entity
                && entity.getBody() instanceof ErrorResponse<?>;
    }

    /**
     * <b>Response'dan ErrorResponse nesnesini çıkarır.</b>
     *
     * @param response Response nesnesi.
     * @return ErrorResponse nesnesi.
     */
    private ErrorResponse<?> extractErrorResponse(Object response) {
        return (ErrorResponse<?>) ((ResponseEntity<?>) response).getBody();
    }
}