package com.kodlamaio.common.constants;

public class Paths {
    public static final String BasePrefix = "/api/v1";
    public static final String IdSuffix = "/{id}";
    public static final String PrometheusMetricsPath = "/actuator/prometheus";
    public static final String ExceptionHandlerBasePackage = "com.kodlamaio.common.configuration.exceptions";
    public static final String ElasticsearchRepositoriesBasePackage = "com.kodlamaio.filterservice.repository";
    public static final String[] SwaggerPaths = {"/swagger-ui/index.html", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**"};

    public static class Inventory {
        public static class Car {
            public static final String Prefix = BasePrefix + "/cars";
            public static final String CheckAvailableSuffix = "/check-car-available" + IdSuffix;
            public static final String GetResponseSuffix = "/get-car-response" + IdSuffix;
            public static final String[] CheckAvailableAndResponsePrefixes = {"/api/v1/cars/check-car-available/**", "/api/v1/cars/get-car-response/**"};
        }

        public static class Brand {

            public static final String Prefix = BasePrefix + "/brands";
        }

        public static class Model {

            public static final String Prefix = BasePrefix + "/models";
        }

        public static final String ServiceBasePackage = "com.kodlamaio.inventoryservice";
    }

    public static class Filter {
        public static final String Prefix = BasePrefix + "/filters";
        public static final String GetByBrandNameSuffix = "/brand";
        public static final String GetByModelNameSuffix = "/model";
        public static final String GetByPlateSuffix = "/plate";
        public static final String GetByModelYearSuffix = "/year";
        public static final String GetByStateSuffix = "/state";
        public static final String PlateSearchSuffix = "/plate/search";
        public static final String BrandSearchSuffix = "/brand/search";
        public static final String ModelSearchSuffix = "/model/search";
        public static final String ServiceBasePackage = "com.kodlamaio.filterservice";
    }

    public static class Rental {
        public static final String Prefix = BasePrefix + "/rentals";
        public static final String ServiceBasePackage = "com.kodlamaio.rentalservice";
    }

    public static class Payment {
        public static final String Prefix = BasePrefix + "/payments";
        public static final String CheckSuffix = "/check";
        public static final String ServiceBasePackage = "com.kodlamaio.paymentservice";
    }

    public static class Invoice {
        public static final String Prefix = BasePrefix + "/invoices";
        public static final String ServiceBasePackage = "com.kodlamaio.invoiceservice";
    }
}
