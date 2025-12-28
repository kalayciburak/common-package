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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.kalayciburak.commonpackage.web.filter.logging.LogContext.populateMDC;
import static com.kalayciburak.commonpackage.web.filter.util.HeaderUtils.getRequestHeadersInfo;
import static com.kalayciburak.commonpackage.web.filter.util.HeaderUtils.getResponseHeadersInfo;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    /**
     * Request ve response body'lerinin önbelleğe alınabileceği maksimum byte sayısı.
     * <p>
     * Büyük payload'ların (file upload, binary içerik vb.) belleği tüketmesini
     * engellemek amacıyla üst sınır olarak kullanılır.
     */
    private static final int MAX_PAYLOAD_LENGTH = 64 * 1024;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    /**
     * Her HTTP isteğini işleyerek API istek ve yanıt detaylarını günlüğe kaydeder.
     *
     * <p>
     * Bu metod, orijinal istek ve yanıtı {@link ContentCachingRequestWrapper} ve
     * {@link ContentCachingResponseWrapper} ile sarmalar. Böylece normalde stream
     * olarak yalnızca bir kez okunabilen body içerikleri, işlem tamamlandıktan sonra
     * güvenli bir şekilde loglanabilir hale gelir.
     * </p>
     *
     * <p>
     * İstek işleme süresi ölçülür, header ve body bilgileri MDC (Mapped Diagnostic Context)
     * içine yazılır ve response içeriği client'a geri aktarılır.
     * </p>
     *
     * @param request     Gelen HTTP isteği
     * @param response    Giden HTTP yanıtı
     * @param filterChain Filter zinciri
     * @throws ServletException Servlet hatası durumunda
     * @throws IOException      IO hatası durumunda
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        var requestWrapper = new ContentCachingRequestWrapper(request, MAX_PAYLOAD_LENGTH);
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
     * HTTP request ve response'a ait header, body ve süre bilgilerini loglar.
     *
     * <p>
     * İçeriğin türüne göre (multipart, binary, image, video, audio vb.) body loglama
     * otomatik olarak atlanabilir. Bu sayede log gürültüsü ve bellek tüketimi kontrol
     * altında tutulur.
     * </p>
     *
     * @param request         HTTP isteği
     * @param responseWrapper Cache'lenmiş HTTP yanıtı
     * @param requestWrapper  Cache'lenmiş HTTP isteği
     * @param executionTime   İstek işleme süresi (ms)
     * @throws IOException Header serileştirme hatası durumunda
     */
    private void logRequestResponse(HttpServletRequest request,
                                    ContentCachingResponseWrapper responseWrapper,
                                    ContentCachingRequestWrapper requestWrapper,
                                    long executionTime) throws IOException {
        var reqCt = request.getContentType();
        var resCt = responseWrapper.getContentType();
        boolean skipReqBody = shouldSkipBody(reqCt);
        boolean skipResBody = shouldSkipBody(resCt);

        var requestBody = skipReqBody
                ? "[SKIPPED: content-type=" + reqCt + "]"
                : toString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        var responseBody = skipResBody
                ? "[SKIPPED: content-type=" + resCt + "]"
                : toString(responseWrapper.getContentAsByteArray(),
                responseWrapper.getCharacterEncoding());

        var requestHeaders = OBJECT_MAPPER.writeValueAsString(getRequestHeadersInfo(request));
        var responseHeaders = OBJECT_MAPPER.writeValueAsString(getResponseHeadersInfo(responseWrapper));

        populateMDC(request, responseWrapper,
                requestBody, responseBody,
                requestHeaders, responseHeaders,
                executionTime);

        log.debug("log_type={}, status_code={}, request_method={}, uri={}",
                LogTypes.API_LOG,
                responseWrapper.getStatus(),
                request.getMethod(),
                request.getRequestURI());
    }

    /**
     * Verilen Content-Type değerine göre body'nin loglanıp loglanmaması gerektiğini belirler.
     *
     * <p>
     * Multipart, binary ve medya içerikleri genellikle büyük veya okunabilir
     * olmadığı için body loglamadan hariç tutulur.
     * </p>
     *
     * @param contentType HTTP Content-Type header değeri
     * @return true ise body loglanmaz, false ise loglanır
     */
    private boolean shouldSkipBody(String contentType) {
        if (contentType == null) return false;
        String ct = contentType.toLowerCase();

        return switch (ct) {
            case String s when s.startsWith("multipart/") -> true;
            case String s when s.startsWith("application/octet-stream") -> true;
            case String s when s.startsWith("image/") -> true;
            case String s when s.startsWith("video/") -> true;
            case String s when s.startsWith("audio/") -> true;
            default -> false;
        };
    }

    /**
     * Byte dizisini güvenli bir şekilde String'e çevirir.
     *
     * <p>
     * Encoding bilgisi yoksa UTF-8 varsayılır. Loglama işlemleri sırasında
     * exception fırlatılmaması özellikle tercih edilmiştir.
     * </p>
     *
     * @param bytes    İçerik byte dizisi
     * @param encoding Karakter encoding bilgisi
     * @return String'e dönüştürülmüş içerik
     */
    private String toString(byte[] bytes, String encoding) {
        Charset charset = (encoding == null)
                ? StandardCharsets.UTF_8
                : Charset.forName(encoding);
        return new String(bytes, charset);
    }
}
