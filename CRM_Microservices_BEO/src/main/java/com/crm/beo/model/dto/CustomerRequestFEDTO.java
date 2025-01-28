package com.crm.beo.model.dto;

import com.crm.beo.model.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestFEDTO {
    private Long id;
    private String customerName;

    private String issueDetails;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
