package com.kalayciburak.commonpackage.core.constant;

public final class Messages {
    private Messages() {}

    public static final class Entity {
        public static final String NOT_FOUND = "Herhangi bir kayıt bulunamadı.";
        public static final String FOUND = "kayıt bulundu.";
        public static final String SAVED = "Veri başarıyla kaydedildi.";
        public static final String UPDATED = "Veri başarıyla güncellendi.";
        public static final String DELETED = "Veri başarıyla silindi.";

        private Entity() {}
    }

    public static final class Entities {
        public static final String NOT_FOUND = "Herhangi bir kayıt bulunamadı.";
        public static final String FOUND = "Kayıtlar listelendi.";
        public static final String SAVED = "Veriler başarıyla kaydedildi.";
        public static final String UPDATED = "Veriler başarıyla güncellendi.";
        public static final String DELETED = "Veriler başarıyla silindi.";

        private Entities() {}
    }

    public static final class Error {
        public static final String UNEXPECTED = "Beklenmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.";
        public static final String INTERNAL_SERVER_ERROR = "Sunucu hatası oluştu. Lütfen daha sonra tekrar deneyiniz.";
        public static final String INVALID_ARGUMENT = "Geçersiz argüman. Lütfen doğru bilgileri giriniz.";
        public static final String VALIDATION_ERROR = "Girilen bilgiler hatalı. Lütfen kontrol ediniz.";
        public static final String ENTITY_NOT_FOUND = "Herhangi bir kayıt bulunamadı.";
        public static final String NO_SUCH_ELEMENT = "Herhangi bir eleman bulunamadı.";
        public static final String RESOURCE_NOT_FOUND = "Kaynak bulunamadı.";
        public static final String ENTITY_EXISTS = "Kayıt zaten mevcut.";
        public static final String UNSUPPORTED_OPERATION = "Desteklenmeyen işlem.";

        private Error() {}
    }

    public static final class LogTemplate {
        private static final String BASE = "Handler: {} | TraceId: {}";

        public static final String WITH_MESSAGE = String.format("%s | Message: {}", BASE);
        public static final String WITH_MESSAGE_AND_DETAIL = String.format("%s | Message: {} | Detail: {}", BASE);
        public static final String WITH_RESPONSE = String.format("%s | Response: {}", BASE);
        public static final String UNEXPECTED_ERROR = String.format("%s | %s", BASE, Error.UNEXPECTED);

        private LogTemplate() {}
    }
}