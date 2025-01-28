package com.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private UUID customerId;

    private String firstName;

    private String lastName;

    private LocalDate customerDate;

    private Boolean isVIP;

    private String statusCode;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;
}
