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

public class ResponseBuilder {
    public static <T> SuccessResponse<T> createSuccessResponse(T data, String message) {
        var code = determineSuccessStatusCode(message);
        int size = getSize(data);

        return new SuccessResponse<>(code, message, size, data);
    }

    public static <T> SuccessResponse<T> createSuccessResponse(String message) {
        return createSuccessResponse(null, message);
    }

    public static <T> SuccessResponse<T> createSuccessResponse(String code, T data, String message) {
        int size = getSize(data);

        return new SuccessResponse<>(code, message, size, data);
    }

    public static <T> SuccessResponse<T> createNotFoundResponse(String message) {
        return new SuccessResponse<>(String.valueOf(NOT_FOUND.value()), message, 0, null);
    }

    private static String determineSuccessStatusCode(String message) {
        return String.valueOf(creationKeywords.stream()
                .map(String::toLowerCase)
                .anyMatch(message.toLowerCase()::contains)
                ? HttpStatus.CREATED.value()
                : HttpStatus.OK.value());
    }

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