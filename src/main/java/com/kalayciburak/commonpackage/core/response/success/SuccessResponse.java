package com.kalayciburak.commonpackage.core.response.success;

import com.kalayciburak.commonpackage.core.response.common.Response;
import lombok.Getter;
import lombok.Setter;

import static com.kalayciburak.commonpackage.core.constant.Types.Response.SUCCESS;

@Getter
@Setter
public class SuccessResponse<T> extends Response {
    private int size;
    private T data;

    public SuccessResponse(String code, String message, int size, T data) {
        super(SUCCESS, code, message, true);
        this.size = size;
        this.data = data;
    }
}