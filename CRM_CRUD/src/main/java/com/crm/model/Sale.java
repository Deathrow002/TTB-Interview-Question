package com.crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "sales")
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "sale_id", nullable = false, updatable = false, unique = true)
    private UUID saleId;

    @Column(name = "customer_id", nullable = false)
    @NotNull
    private UUID customerId;

    @Column(name = "sale_amount", nullable = false)
    @NotNull
    @Positive
    private BigDecimal saleAmount;

    @Column(name = "sale_date", nullable = false)
    @NotNull
    private Timestamp saleDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @PrePersist
    public void generateUUID() {
        if (saleId == null) {
            saleId = UUID.randomUUID();
        }
    }
}
