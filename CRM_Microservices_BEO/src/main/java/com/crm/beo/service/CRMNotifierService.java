package com.crm.beo.service;

import com.crm.beo.model.RequestStatus;
import com.crm.beo.model.dto.CustomerRequestFEDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CRMNotifierService {
    private static final Logger logger = LoggerFactory.getLogger(CRMNotifierService.class);
    private static final String HEADER_IDENTIFY = "beo";

    private final RestTemplate restTemplate;

    @Autowired
    public CRMNotifierService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add("Header-Identify", HEADER_IDENTIFY);
                    return execution.execute(request, body);
                })
                .build();
    }

    /**
     * Sends a notification to update the status of a Customer Request.
     *
     * @param crmRequestId the ID of the Customer Request
     * @param status       the new status to update
     */
    public void notifyCRM(Long crmRequestId, RequestStatus status) {
        String crmServiceUrl = "http://localhost:8081/api/customer-requests/" + crmRequestId + "/status";

        try {
            ResponseEntity<?> response = restTemplate.exchange(
                    crmServiceUrl + "?status=" + status.name(),
                    HttpMethod.PUT,
                    createRequestEntity(null),
                    Void.class
            );
            logger.info("Successfully notified CRM for updateStatus: {}", response.getStatusCode());
        } catch (Exception e) {
            logger.error("Failed to notify CRM for updateStatus: {}", e.getMessage(), e);
        }
    }

    /**
     * Sends a notification to cancel a Customer Request.
     *
     * @param crmRequestId the ID of the Customer Request to cancel
     */
    public void notifyCancel(Long crmRequestId) {
        String crmServiceUrl = "http://localhost:8081/api/customer-requests/" + crmRequestId + "/cancel";

        try {
            ResponseEntity<?> response = restTemplate.exchange(
                    crmServiceUrl,
                    HttpMethod.PUT,
                    createRequestEntity(null),
                    Void.class
            );
            logger.info("Successfully notified CRM for cancelRequest: {}", response.getStatusCode());
        } catch (Exception e) {
            logger.error("Failed to notify CRM for cancelRequest: {}", e.getMessage(), e);
        }
    }

    /**
     * Helper method to create an HttpEntity with headers.
     *
     * @param body the body of the request (can be null)
     * @return the HttpEntity object
     */
    private HttpEntity<?> createRequestEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Header-Identify", HEADER_IDENTIFY);
        return new HttpEntity<>(body, headers);
    }
}
