package com.crm.feo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "request_feo")
public class CustomerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String issueDetails;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
