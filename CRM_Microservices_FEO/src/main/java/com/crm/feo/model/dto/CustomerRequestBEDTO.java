package com.crm.feo.model.dto;

import com.crm.feo.model.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestBEDTO {
    private Long crmRequestId; // Links to the CRM request ID

    private String assignedTo;

    private String resolutionDetails;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
