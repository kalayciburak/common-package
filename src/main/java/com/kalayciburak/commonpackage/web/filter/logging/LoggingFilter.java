package com.kalayciburak.commonpackage.web.filter.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalayciburak.commonpackage.web.filter.constant.LogTypes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.kalayciburak.commonpackage.web.filter.logging.LogContext.populateMDC;
import static com.kalayciburak.commonpackage.web.filter.util.HeaderUtils.getRequestHeadersInfo;
import static com.kalayciburak.commonpackage.web.filter.util.HeaderUtils.getResponseHeadersInfo;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    /**
     * Her HTTP isteğini işleyerek API istek ve yanıt detaylarını günlüğe kaydeder.
     *
     * <p>
     * Bu metod, orijinal istek ve yanıtı önbellekleme sarmalayıcıları (wrapper) ile sarmalar, böylece içerik akışlarını
     * okuyabilir. Ardından isteğin işlenme süresini ölçer, başlıklar, gövdeler ve diğer meta verileri Mapped Diagnostic
     * Context (MDC) kullanarak loglar. Son olarak, yanıt içeriğinin istemciye iletilmesini sağlar ve MDC'yi temizler.
     * </p>
     *
     * @param request     Gelen HTTP isteğini temsil eder
     * @param response    Giden HTTP yanıtını temsil eder
     * @param filterChain İsteği ve yanıtı zincirdeki bir sonraki elemana iletmek için kullanılan FilterChain nesnesi
     * @throws ServletException Servlet hatası durumunda fırlatılır
     * @throws IOException      Giriş/çıkış hatası durumunda fırlatılır
     * @implNote <i>Normalde request ve response body'leri stream olarak okunur ve akış tamamlandığında tekrar erişilemez;
     * {@code ContentCachingRequestWrapper} ve {@code ContentCachingResponseWrapper} sınıfları, bu akışları önbelleğe alır ve
     * daha sonra tekrar okunabilir hale getirir.</i>
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        var requestWrapper = new ContentCachingRequestWrapper(request);
        var responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            logRequestResponse(request, responseWrapper, requestWrapper, executionTime);
            responseWrapper.copyBodyToResponse();
            MDC.clear();
        }
    }

    /**
     * API istek ve yanıt bilgilerini, başlıkları, gövdeleri ve işleme süresini içerecek şekilde günlüğe kaydeder.
     *
     * @param request         İlgili HTTP isteğini temsil eden HttpServletRequest nesnesi
     * @param responseWrapper Yanıtı içeren ContentCachingResponseWrapper nesnesi
     * @param requestWrapper  İsteği içeren ContentCachingRequestWrapper nesnesi
     * @param executionTime   İsteğin işlenme süresi (ms cinsinden)
     * @throws IOException Karakter encode hatası durumunda fırlatılır
     */
    private void logRequestResponse(HttpServletRequest request,
                                    ContentCachingResponseWrapper responseWrapper,
                                    ContentCachingRequestWrapper requestWrapper,
                                    long executionTime) throws IOException {
        var requestBody = getJsonString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        var responseBody = getJsonString(responseWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        var requestHeaders = OBJECT_MAPPER.writeValueAsString(getRequestHeadersInfo(request));
        var responseHeaders = OBJECT_MAPPER.writeValueAsString(getResponseHeadersInfo(responseWrapper));

        populateMDC(request, responseWrapper, requestBody, responseBody, requestHeaders, responseHeaders, executionTime);

        log.debug("log_type={}, status_code={}, request_method={}, uri={}",
                LogTypes.API_LOG, responseWrapper.getStatus(), request.getMethod(), request.getRequestURI());
    }

    /**
     * Verilen byte dizisini belirtilen karakter encode'ine göre String'e dönüştürür.
     *
     * @param contentAsByteArray Byte dizisi
     * @param characterEncoding  Kullanılacak karakter encode'i
     * @return Byte dizisinden elde edilen String değer
     * @throws RuntimeException Belirtilen encode desteklenmiyorsa fırlatılır
     */
    private String getJsonString(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, characterEncoding);
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }
}