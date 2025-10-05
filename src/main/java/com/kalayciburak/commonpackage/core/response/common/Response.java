package com.kalayciburak.commonpackage.core.response.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kalayciburak.commonpackage.core.util.TraceIdGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <b>Tüm response tiplerinin base class'ı.</b>
 * <p>
 * Her response için otomatik olarak timestamp ve traceId üretir.
 * TraceId, request'in tüm yaşam döngüsü boyunca log kayıtlarında izlenebilmesini sağlar.
 */
@Getter
@Setter
public abstract class Response {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime timestamp;
    protected String traceId;
    protected String type;
    protected String code;
    protected Object message;
    protected boolean success;

    /**
     * <b>Response nesnesi oluşturur ve otomatik olarak timestamp ile traceId atar.</b>
     *
     * @param type    Response tipi (SUCCESS, ERROR, vb.).
     * @param code    HTTP status code veya özel durum kodu.
     * @param message Kullanıcıya gösterilecek mesaj.
     * @param success İşlemin başarılı olup olmadığı.
     */
    public Response(String type, String code, Object message, boolean success) {
        this.timestamp = LocalDateTime.now();
        this.traceId = TraceIdGenerator.generate();
        this.type = type;
        this.code = code;
        this.message = message;
        this.success = success;
    }

    /**
     * <b>Mevcut bir response nesnesinden kopyalama yaparken kullanılır.</b>
     * <p>
     * TraceId aynı kalır, böylece aynı request için tüm log kayıtları ilişkilendirilir.
     *
     * @param type    Response tipi.
     * @param code    HTTP status code.
     * @param message Kullanıcıya gösterilecek mesaj.
     * @param success İşlemin başarılı olup olmadığı.
     * @param traceId Mevcut request'in traceId'si.
     */
    protected Response(String type, String code, Object message, boolean success, String traceId) {
        this.timestamp = LocalDateTime.now();
        this.traceId = traceId;
        this.type = type;
        this.code = code;
        this.message = message;
        this.success = success;
    }
}