# Common Package

Bu paket, birden fazla **Spring Boot** projesinde tekrar kullanılabilir **yardımcı sınıflar**, **konfigürasyonlar** ve *
*bileşenler** sunar.

## 🚀 Özellikler

- 📦 **Genel yardımcı (utility) sınıflar ve metodlar**
- ⚙️ **Paylaşılan konfigürasyonlar**
- ♻️ **Yeniden kullanılabilir bileşenler**
- 📜 **OpenAPI dökümantasyon desteği**
- 📝 **Logback GELF ile güçlü loglama konfigürasyonu**
- 🎯 **Merkezi hata yönetimi**
- 🏗 **Aspect-Oriented Programming (AOP) yardımcıları**

## 🛠 Gereksinimler

- **Java 21** veya daha üstü
- **Maven 3.6.3** veya daha üstü
- **Spring Boot 3.4.3**

## 📥 Kurulum

Projende kullanmak için aşağıdaki bağımlılığı `pom.xml` dosyanıza ekleyin:

```xml

<dependency>
    <groupId>com.kalayciburak</groupId>
    <artifactId>common-package</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 🏗 GitHub Packages Konfigürasyonu

Paketi **GitHub Packages** üzerinden almak için, aşağıdaki repository ayarlarını `pom.xml` dosyanıza ekleyin:

```xml

<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/kalayciburak/common-package</url>
    </repository>
</repositories>
```

Ayrıca, **Maven `settings.xml`** dosyanıza **GitHub kimlik bilgilerinizi** eklemeyi unutmayın:

```xml

<settings>
    <servers>
        <server>
            <id>github</id>
            <username>YOUR_GITHUB_USERNAME</username>
            <password>YOUR_GITHUB_TOKEN</password>
        </server>
    </servers>
</settings>
```

## 📌 Bağımlılıklar

Bu paket aşağıdaki **Spring Boot ve yardımcı kütüphaneleri** kullanır:

- **Spring Boot Starter Web**
- **Spring Data Commons**
- **SpringDoc OpenAPI UI**
- **Logback GELF** (Loglama için)
- **Lombok** (Kod sadeleştirme için)
- **AspectJ Weaver** (AOP desteği için)

## 🚀 Kullanım

Paket, Spring Boot projelerinde hız kazandıran çeşitli yardımcı araçlar içerir. Başlıca bileşenler:

### 📑 OpenAPI Dokümantasyonu

SpringDoc OpenAPI entegrasyonu sayesinde API dokümantasyonunu otomatik olarak oluşturabilirsiniz.

### 📊 Loglama Konfigürasyonu

Logback GELF desteği ile yapılandırılabilir ve merkezi loglama sistemiyle kolay entegrasyon sağlar.

### 🛠 Genel Yardımcı Sınıflar

Sık kullanılan fonksiyonlar için hazır utility metodlar.

### ❗ Genel Hata Yönetimi

Merkezi exception handling mekanizması sayesinde temiz ve düzenli hata yönetimi sağlar.

### 🎭 AOP Destekli Çözümler

Aspect-Oriented Programming desteği ile **loglama, güvenlik, izleme** gibi işlemleri kolayca yönetebilirsiniz.

## 🤝 Katkıda Bulun

Her türlü katkıya açığız! **Pull Request** göndermekten çekinmeyin. 🚀

## 📜 Lisans

Bu proje, repoda belirtilen lisans koşulları altında sunulmaktadır.

## 📬 İletişim

👨‍💻 **Geliştirici:** Burak Kalaycı  
📧 **E-posta:** kalayciburak1996@gmail.com  
🐙 **GitHub:** [kalayciburak](https://github.com/kalayciburak)

---

> "Kod tekrarını değil, kodun gücünü paylaş!" 💡