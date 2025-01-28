package com.crm.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {

    @NotNull
    private UUID customerId;

    @NotNull
    @Positive
    private BigDecimal saleAmount;

    @NotNull
    private Timestamp saleDate;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
