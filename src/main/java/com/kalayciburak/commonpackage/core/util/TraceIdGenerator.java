package com.kalayciburak.commonpackage.core.util;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * <b>Her request için benzersiz bir traceId üretir ve MDC'ye ekler.</b>
 * <p>
 * TraceId, bir request'in tüm yaşam döngüsü boyunca izlenebilmesini sağlar.
 * Hem response'larda hem de log kayıtlarında kullanılarak hata takibi kolaylaştırılır.
 */
public final class TraceIdGenerator {
    private static final String TRACE_ID_KEY = "traceId";

    private TraceIdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * <b>Yeni bir traceId üretir, MDC'ye ekler ve döner.</b>
     *
     * @return Üretilen benzersiz traceId.
     */
    public static String generate() {
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID_KEY, traceId);
        return traceId;
    }

    /**
     * <b>MDC'den mevcut traceId'yi alır.</b>
     *
     * @return Mevcut traceId varsa döner, yoksa null döner.
     */
    public static String get() {
        return MDC.get(TRACE_ID_KEY);
    }

    /**
     * <b>Verilen traceId'yi MDC'ye ekler.</b>
     *
     * @param traceId Eklenecek traceId.
     */
    public static void set(String traceId) {
        if (traceId != null && !traceId.isEmpty()) {
            MDC.put(TRACE_ID_KEY, traceId);
        }
    }

    /**
     * <b>MDC'den traceId'yi temizler.</b>
     */
    public static void clear() {
        MDC.remove(TRACE_ID_KEY);
    }
}
