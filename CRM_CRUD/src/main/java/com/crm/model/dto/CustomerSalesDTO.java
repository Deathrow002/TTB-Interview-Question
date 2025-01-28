package com.crm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomerSalesDTO {
    private UUID customerId;
    private BigDecimal totalSales;
    private int rank;

    // Constructor to match the query result type (String for customer_id)
    public CustomerSalesDTO(String customerId, BigDecimal totalSales, int rank) {
        this.customerId = UUID.fromString(customerId);  // Convert String to UUID
        this.totalSales = totalSales;
        this.rank = rank;
    }
}


