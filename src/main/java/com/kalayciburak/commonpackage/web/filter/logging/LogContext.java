package com.kalayciburak.commonpackage.web.filter.logging;

import com.kalayciburak.commonpackage.web.filter.constant.LogTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;

public class LogContext {
    private LogContext() {}

    /**
     * Günlük kayıt işlemleri için Mapped Diagnostic Context (MDC) içerisine istek ve yanıt bilgilerini ekler.
     *
     * @param request         HTTP isteğini temsil eden HttpServletRequest nesnesi
     * @param response        HTTP yanıtını temsil eden HttpServletResponse nesnesi (veya önbellekleme sarmalayıcısı)
     * @param requestBody     İstek gövdesinin String formatındaki hali
     * @param responseBody    Yanıt gövdesinin String formatındaki hali
     * @param requestHeaders  İstek başlıklarının JSON formatındaki hali
     * @param responseHeaders Yanıt başlıklarının JSON formatındaki hali
     * @param executionTime   İsteğin işlenme süresi (ms cinsinden)
     */
    public static void populateMDC(HttpServletRequest request,
                                   HttpServletResponse response,
                                   String requestBody,
                                   String responseBody,
                                   String requestHeaders,
                                   String responseHeaders,
                                   long executionTime) {
        addBasicRequestInfo(request, response, executionTime);
        addClientAndServerInfo(request);
        addTechnicalRequestDetails(request);
        addAuthenticationInfo(request);
        addRequestResponseContent(requestBody, responseBody, requestHeaders, responseHeaders);
    }

    /**
     * Temel API isteği ve yanıt bilgilerini MDC içine ekler.
     *
     * @param request       HTTP isteği
     * @param response      HTTP yanıtı
     * @param executionTime İsteğin işlenme süresi (ms)
     */
    private static void addBasicRequestInfo(HttpServletRequest request, HttpServletResponse response, long executionTime) {
        MDC.put("log_type", LogTypes.API_LOG);
        MDC.put("request_method", request.getMethod());
        MDC.put("uri", request.getRequestURI());
        MDC.put("query_string", request.getQueryString() != null ? request.getQueryString() : "");
        MDC.put("status_code", String.valueOf(response.getStatus()));
        MDC.put("execution_time_ms", String.valueOf(executionTime));
    }

    /**
     * İstemci (client) ve sunucu (server) ile ilgili bilgileri MDC içine ekler.
     *
     * @param request HTTP isteği
     */
    private static void addClientAndServerInfo(HttpServletRequest request) {
        MDC.put("remote_address", request.getRemoteAddr());
        MDC.put("remote_port", String.valueOf(request.getRemotePort()));
        MDC.put("server_address", request.getLocalAddr());
        MDC.put("server_port", String.valueOf(request.getLocalPort()));
    }

    /**
     * İstek ile ilgili teknik detayları MDC içine ekler.
     *
     * @param request HTTP isteği
     */
    private static void addTechnicalRequestDetails(HttpServletRequest request) {
        MDC.put("content_type", request.getContentType());
        MDC.put("content_length", String.valueOf(request.getContentLength()));
        MDC.put("character_encoding", request.getCharacterEncoding());
        MDC.put("protocol", request.getProtocol());
        MDC.put("scheme", request.getScheme());
        MDC.put("secure", String.valueOf(request.isSecure()));
        MDC.put("user_agent", request.getHeader("User-Agent"));
    }

    /**
     * Kimlik doğrulama bilgilerini (varsa) MDC içine ekler.
     *
     * @param request HTTP isteği
     */
    private static void addAuthenticationInfo(HttpServletRequest request) {
        if (request.getRemoteUser() != null) MDC.put("remote_user", request.getRemoteUser());
    }

    /**
     * İstek ve yanıt içeriği ile başlık bilgilerini MDC içine ekler.
     *
     * @param requestBody     İstek gövdesi
     * @param responseBody    Yanıt gövdesi
     * @param requestHeaders  İstek başlıkları (JSON formatında)
     * @param responseHeaders Yanıt başlıkları (JSON formatında)
     */
    private static void addRequestResponseContent(String requestBody, String responseBody, String requestHeaders, String responseHeaders) {
        MDC.put("request_body", requestBody);
        MDC.put("response_body", responseBody);
        MDC.put("request_headers", requestHeaders);
        MDC.put("response_headers", responseHeaders);
    }
}
