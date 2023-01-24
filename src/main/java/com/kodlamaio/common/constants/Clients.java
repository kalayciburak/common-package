package com.kodlamaio.common.constants;

public class Clients {
    // Base
    public static final String BaseUrl = "http://localhost:9010/";
    public static final String BaseIdSuffix = "/{id}";

    // Payment Client
    public static final String PaymentClient = "paymentclient";
    public static final String PaymentClientCheck = "payment-service/api/v1/payments/check";

    // Car Client
    public static final String CarClient = "carclient";
    public static final String CarClientCheckAvailable = "inventory-service/api/v1/cars/check-car-available" + BaseIdSuffix;
    public static final String CarClientGetResponse = "inventory-service/api/v1/cars/get-car-response" + BaseIdSuffix;
}