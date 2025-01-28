package com.crm.beo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "request_beo")
public class CustomerRequest {
    @Id
    private Long crmRequestId; // Links to the CRM request ID and acts as the primary key

    private String assignedTo;

    private String resolutionDetails;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}