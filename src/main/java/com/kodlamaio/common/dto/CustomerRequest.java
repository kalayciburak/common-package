package com.kodlamaio.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String customerId;
    private String customerUserName;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
}
