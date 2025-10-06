package com.kalayciburak.commonpackage.core.response.error;

import lombok.Getter;
import lombok.Setter;

/**
 * <b>Hata detaylarını loglama amacıyla saklayan DTO.</b>
 * <p>
 * Bu bilgiler frontend'e gönderilmez ({@code @JsonIgnore} ile işaretlenmiştir).
 * Sadece Graylog'da traceId ile birlikte kaydedilir ve hata takibi için kullanılır.
 */
@Setter
@Getter
public class ErrorDetail {
    private String className;
    private String methodName;
    private Integer lineNumber;
    private String debugMessage;
    private String exceptionType;

    private static final int NO_LINE_NUMBER = 0;
    private static final String UNKNOWN = "Bilinmiyor";
    private static final String NO_MESSAGE = "Mesaj yok";

    /**
     * <b>Throwable nesnesinden hata detaylarını çıkarır.</b>
     * <p>
     * Stack trace'in ilk elemanından sınıf adı, metod adı ve satır numarası alınır.
     * Exception mesajı ve tipi de saklanır.
     *
     * @param cause Hata nedeni.
     */
    public ErrorDetail(Throwable cause) {
        boolean hasStackTrace = cause != null && cause.getStackTrace().length > 0;
        if (hasStackTrace) fillFromCause(cause);
        else fillWithDefaults();
    }

    /**
     * <b>Hata detaylarını okunabilir string formatında döner.</b>
     * <p>
     * Loglama için kullanılır.
     *
     * @return Formatlanmış hata detayı string'i.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s sınıfındaki %s metodunda (satır %d): %s",
                exceptionType != null ? exceptionType : UNKNOWN,
                className != null ? className : UNKNOWN,
                methodName != null ? methodName : UNKNOWN,
                lineNumber != null ? lineNumber : NO_LINE_NUMBER,
                debugMessage != null ? debugMessage : NO_MESSAGE);
    }

    /**
     * <b>Throwable nesnesinden hata detaylarını doldurur.</b>
     * <p>
     * Stack trace'in ilk elemanından sınıf, metod ve satır bilgilerini alır.
     * Ayrıca exception tipi ve mesajı da kaydedilir.
     *
     * @param cause Hata nedeni (stack trace içermelidir).
     */
    private void fillFromCause(Throwable cause) {
        var element = cause.getStackTrace()[0];
        this.className = element.getClassName();
        this.methodName = element.getMethodName();
        this.lineNumber = element.getLineNumber();
        this.debugMessage = cause.getMessage();
        this.exceptionType = cause.getClass().getSimpleName();
    }

    /**
     * <b>Geçerli bir {@link Throwable} nesnesi bulunamadığında varsayılan değerleri atar.</b>
     * <p>
     * Tüm alanlar "bilinmiyor" veya "mesaj yok" gibi sabit değerlerle doldurulur.
     */
    private void fillWithDefaults() {
        this.className = UNKNOWN;
        this.methodName = UNKNOWN;
        this.lineNumber = NO_LINE_NUMBER;
        this.debugMessage = NO_MESSAGE;
        this.exceptionType = UNKNOWN;
    }
}