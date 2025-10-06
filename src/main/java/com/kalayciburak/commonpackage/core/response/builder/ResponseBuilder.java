package com.kalayciburak.commonpackage.core.response.builder;

import com.kalayciburak.commonpackage.core.response.common.Sizable;
import com.kalayciburak.commonpackage.core.response.success.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.kalayciburak.commonpackage.core.constant.Keywords.creationKeywords;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * <b>Success response nesnelerini oluşturan builder class'ı.</b>
 * <p>
 * Her response için otomatik olarak traceId üretilir ve MDC'ye eklenir.
 * Bu sayede request yaşam döngüsü boyunca tüm loglar ilişkilendirilebilir.
 */
public class ResponseBuilder {
    private ResponseBuilder() {}

    /**
     * <b>Data ve mesaj ile success response oluşturur.</b>
     * <p>
     * HTTP status code, mesaj içeriğine göre otomatik belirlenir (CREATED veya OK).
     * TraceId otomatik üretilir ve response'a eklenir.
     *
     * @param data    Response'a eklenecek data.
     * @param message Kullanıcıya gösterilecek mesaj.
     * @param <T>     Data tipi.
     * @return Oluşturulan {@link SuccessResponse} nesnesi.
     */
    public static <T> SuccessResponse<T> createSuccessResponse(T data, String message) {
        var code = determineSuccessStatusCode(message);
        int size = getSize(data);

        return new SuccessResponse<>(code, message, size, data);
    }

    /**
     * <b>Sadece mesaj ile success response oluşturur.</b>
     * <p>
     * Data olmadan response döner. TraceId otomatik üretilir.
     *
     * @param message Kullanıcıya gösterilecek mesaj.
     * @param <T>     Data tipi.
     * @return Oluşturulan {@link SuccessResponse} nesnesi.
     */
    public static <T> SuccessResponse<T> createSuccessResponse(String message) {
        return createSuccessResponse(null, message);
    }

    /**
     * <b>Özel status code ile success response oluşturur.</b>
     * <p>
     * TraceId otomatik üretilir.
     *
     * @param code    HTTP status code.
     * @param data    Response'a eklenecek data.
     * @param message Kullanıcıya gösterilecek mesaj.
     * @param <T>     Data tipi.
     * @return Oluşturulan {@link SuccessResponse} nesnesi.
     */
    public static <T> SuccessResponse<T> createSuccessResponse(String code, T data, String message) {
        int size = getSize(data);

        return new SuccessResponse<>(code, message, size, data);
    }

    /**
     * <b>Not found durumu için success response oluşturur.</b>
     * <p>
     * HTTP 404 status code ile döner. TraceId otomatik üretilir.
     *
     * @param message Kullanıcıya gösterilecek mesaj.
     * @param <T>     Data tipi.
     * @return Oluşturulan {@link SuccessResponse} nesnesi.
     */
    public static <T> SuccessResponse<T> createNotFoundResponse(String message) {
        return new SuccessResponse<>(String.valueOf(NOT_FOUND.value()), message, 0, null);
    }

    /**
     * <b>Mesaj içeriğine göre HTTP status code belirler.</b>
     *
     * @param message Kontrol edilecek mesaj.
     * @return CREATED (201) veya OK (200) status code.
     */
    private static String determineSuccessStatusCode(String message) {
        return String.valueOf(creationKeywords.stream()
                .map(String::toLowerCase)
                .anyMatch(message.toLowerCase()::contains)
                ? HttpStatus.CREATED.value()
                : HttpStatus.OK.value());
    }

    /**
     * <b>Data'nın boyutunu hesaplar.</b>
     * <p>
     * Farklı collection tiplerini ve custom Sizable interface'ini destekler.
     *
     * @param data Boyutu hesaplanacak data.
     * @return Data boyutu. Null veya tek bir nesne için 1 döner.
     */
    private static int getSize(Object data) {
        return switch (data) {
            case List<?> list -> list.size();
            case Page<?> page -> page.getSize();
            case Set<?> set -> set.size();
            case Collection<?> collection -> collection.size();
            case Object[] array -> array.length;
            case Sizable sizable -> sizable.size();
            case null, default -> 1;
        };
    }
}