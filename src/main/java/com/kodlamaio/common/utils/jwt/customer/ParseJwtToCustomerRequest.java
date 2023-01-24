package com.kodlamaio.common.utils.jwt.customer;

import com.kodlamaio.common.constants.JwtClaims;
import com.kodlamaio.common.dto.CustomerRequest;
import org.springframework.security.oauth2.jwt.Jwt;

public class ParseJwtToCustomerRequest {
    public static CustomerRequest getCustomerInformation(Jwt jwt) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerId(jwt.getClaimAsString(JwtClaims.CUSTOMER_ID));
        customerRequest.setCustomerUserName(jwt.getClaimAsString(JwtClaims.CUSTOMER_USER_NAME));
        customerRequest.setCustomerFirstName(jwt.getClaimAsString(JwtClaims.CUSTOMER_FIRST_NAME));
        customerRequest.setCustomerLastName(jwt.getClaimAsString(JwtClaims.CUSTOMER_LAST_NAME));
        customerRequest.setCustomerEmail(jwt.getClaimAsString(JwtClaims.CUSTOMER_EMAIL));
        return customerRequest;
    }
}