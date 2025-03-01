package com.kalayciburak.commonpackage.core.mapper;

public interface BaseMapper<RESPONSE, ENTITY> {
    RESPONSE toResponse(ENTITY entity);

    ENTITY toEntity(RESPONSE response);
}