package com.kalayciburak.commonpackage.core.response.success;

import com.kalayciburak.commonpackage.core.response.common.Response;
import lombok.Getter;
import lombok.Setter;

import static com.kalayciburak.commonpackage.core.constant.Types.Response.SUCCESS;

/**
 * <b>Başarılı işlemleri temsil eden response nesnesi.</b>
 * <p>
 * Her response için otomatik olarak traceId üretilir ve MDC'ye eklenir.
 * TraceId, request'in tüm yaşam döngüsü boyunca log kayıtlarında izlenebilmesini sağlar.
 */
@Getter
@Setter
public class SuccessResponse<T> extends Response {
    private int size;
    private T data;

    /**
     * <b>Success response nesnesi oluşturur.</b>
     * <p>
     * TraceId otomatik üretilir ve hem response'a hem de MDC'ye eklenir.
     *
     * @param code    HTTP status code.
     * @param message Kullanıcıya gösterilecek başarı mesajı.
     * @param size    Dönen data'nın boyutu.
     * @param data    Response'da dönen data.
     */
    public SuccessResponse(String code, String message, int size, T data) {
        super(SUCCESS, code, message, true);
        this.size = size;
        this.data = data;
    }
}