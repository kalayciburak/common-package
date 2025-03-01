package com.kalayciburak.commonpackage.web.filter.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class HeaderUtils {
    /**
     * HTTP isteğine ait tüm başlık isimlerini ve karşılık gelen değerlerini içeren bir map yapısını döndürür.
     *
     * @param request HTTP isteğini temsil eden HttpServletRequest nesnesi
     * @return İstek başlıklarının isim ve değerlerini içeren Map yapısı
     */
    public static Map<String, String> getRequestHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        var headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            var key = headerNames.nextElement();
            var value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    /**
     * HTTP yanıtına ait tüm başlık isimlerini ve karşılık gelen değerlerini içeren bir map yapısını döndürür.
     *
     * @param response HTTP yanıtını temsil eden HttpServletResponse nesnesi
     * @return Yanıt başlıklarının isim ve değerlerini içeren Map yapısı
     */
    public static Map<String, String> getResponseHeadersInfo(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        for (String name : response.getHeaderNames()) {
            var value = response.getHeader(name);
            map.put(name, value);
        }

        return map;
    }
}
