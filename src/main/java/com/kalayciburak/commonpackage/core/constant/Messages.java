package com.kalayciburak.commonpackage.core.constant;

public final class Messages {
    public static final class Entity {
        public static final String NOT_FOUND = "Herhangi bir kayıt bulunamadı.";
        public static final String FOUND = "kayıt bulundu.";
        public static final String SAVED = "Veri başarıyla kaydedildi.";
        public static final String UPDATED = "Veri başarıyla güncellendi.";
        public static final String DELETED = "Veri başarıyla silindi.";
    }

    public static final class Entities {
        public static final String NOT_FOUND = "Herhangi bir kayıt bulunamadı.";
        public static final String FOUND = "Kayıtlar listelendi.";
        public static final String SAVED = "Veriler başarıyla kaydedildi.";
        public static final String UPDATED = "Veriler başarıyla güncellendi.";
        public static final String DELETED = "Veriler başarıyla silindi.";
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
    }

    public static final class Inventory {
        public static final class Product {
            public static final String NOT_FOUND = "Herhangi bir ürün bulunamadı.";
            public static final String FOUND = "Ürün bulundu.";
            public static final String LISTED = "Ürünler listelendi.";
            public static final String SAVED = "Ürün oluşturuldu.";
            public static final String UPDATED = "Ürün güncellendi.";
            public static final String DELETED = "Ürün silindi.";
        }

        public static final class Attribute {
            public static final String NOT_FOUND = "Herhangi bir özellik bulunamadı.";
            public static final String FOUND = "Özellik bulundu.";
            public static final String LISTED = "Özellikler listelendi.";
            public static final String SAVED = "Özellik oluşturuldu.";
            public static final String UPDATED = "Özellik güncellendi.";
            public static final String DELETED = "Özellik silindi.";
        }

        public static final class Image {
            public static final String NOT_FOUND = "Herhangi bir resim bulunamadı.";
            public static final String FOUND = "Resim bulundu.";
            public static final String LISTED = "Resimler listelendi.";
            public static final String SAVED = "Resim oluşturuldu.";
            public static final String UPDATED = "Resim güncellendi.";
            public static final String DELETED = "Resim silindi.";
        }

        public static final class Review {
            public static final String NOT_FOUND = "Herhangi bir yorum bulunamadı.";
            public static final String FOUND = "Yorum bulundu.";
            public static final String LISTED = "Yorumlar listelendi.";
            public static final String SAVED = "Yorum oluşturuldu.";
            public static final String UPDATED = "Yorum güncellendi.";
            public static final String DELETED = "Yorum silindi.";
        }

        public static final class Category {
            public static final String NOT_FOUND = "Herhangi bir kategori bulunamadı.";
            public static final String FOUND = "Kategori bulundu.";
            public static final String LISTED = "Kategoriler listelendi.";
            public static final String SAVED = "Kategori oluşturuldu.";
            public static final String UPDATED = "Kategori güncellendi.";
            public static final String DELETED = "Kategori silindi.";
            public static final String SUBCATEGORY_INFO = "Kategori ve alt kategori bilgileri getirildi.";
            public static final String PARENT_CATEGORIES_INFO = "Ana kategoriler ve alt kategori bilgileri getirildi.";
            public static final String EXISTS = "Kategori zaten mevcut.";
        }
    }

    public static final class User {
        public static final String NOT_FOUND = "Herhangi bir kullanıcı bulunamadı.";
        public static final String FOUND = "Kullanıcı bulundu.";
        public static final String LISTED = "Kullanıcılar listelendi.";
        public static final String SAVED = "Kullanıcı oluşturuldu.";
        public static final String UPDATED = "Kullanıcı güncellendi.";
        public static final String DELETED = "Kullanıcı silindi.";
        public static final String EXISTS = "Kullanıcı zaten mevcut.";
        public static final String REGISTER_SUCCESS = "Kullanıcı başarıyla kaydedildi.";
        public static final String PASSWORD_UPDATED = "Şifre başarıyla güncellendi.";
        public static final String PASSWORD_CHANGED = "Şifre başarıyla değiştirildi.";
        public static final String ROLES_UPDATED = "Kullanıcı rolleri başarıyla güncellendi.";
        public static final String ROLES_NOT_CHANGED = "Herhangi bir rol değişikliği yapılmadı.";
    }

    public static final class Auth {
        public static final String LOGIN_SUCCESS = "Başarıyla giriş yapıldı.";
        public static final String LOGOUT_SUCCESS = "Başarıyla çıkış yapıldı.";
        public static final String REFRESH_SUCCESS = "Token başarıyla yenilendi.";
        public static final String TOKEN_TYPE_MISMATCH = "Token türü uyuşmuyor.";
        public static final String TOKEN_BLACKLISTED = "Token karalisteye alınmış.";
        public static final String OLD_PASSWORD_NOT_MATCH = "Eski şifre eşleşmiyor.";
        public static final String INVALID_ROLE_IDS = "Geçersiz rol id'leri.";
        public static final String INVALID_JWT = "Geçersiz veya süresi dolmuş JWT.";
        public static final String BREACHED_PASSWORD = "Şifre güvenli değil.";
        public static final String ADMIN_CANNOT_BE_DELETED = "Admin kullanıcı silinemez.";
        public static final String INVALID_CREDENTIALS = "Geçersiz kullanıcı adı veya şifre.";
        public static final String ACCESS_DENIED = "Erişim reddedildi.";
    }
}