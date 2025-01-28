package com.crm.feo.service;

import com.crm.feo.model.CustomerRequest;
import com.crm.feo.model.RequestStatus;
import com.crm.feo.model.dto.CustomerRequestBEDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class CRMNotifierService {

    private static final Logger logger = LoggerFactory.getLogger(CRMNotifierService.class);
    private final RestTemplate restTemplate;

    @Autowired
    public CRMNotifierService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .additionalInterceptors(new HeaderInterceptor()) // Add the interceptor
                .build();
    }

    /**
     * Notify Back-End Office with a new CustomerRequest to trigger the receiveRequest service.
     *
     * @param request The CustomerRequest to send.
     */
    public void notifyReceive(CustomerRequest request) {
        String url = "http://localhost:8082/api/backoffice/receive";
        CustomerRequestBEDTO payload = new CustomerRequestBEDTO();
        payload.setCrmRequestId(request.getId());
        payload.setResolutionDetails(request.getIssueDetails());
        payload.setStatus(request.getStatus());
        try {
            restTemplate.postForEntity(url, payload, CustomerRequestBEDTO.class);
            logger.info("Successfully notified Back-End Office for receiveRequest.");
        } catch (Exception e) {
            logger.error("Failed to notify Back-End Office for receiveRequest", e);
            throw new CRMNotificationException("Failed to notify Back-End Office", e);
        }
    }

    /**
     * Notify Back-End Office to cancel a CustomerRequest.
     *
     * @param crmRequestId The ID of the request to cancel.
     */
    public void notifyCancel(Long crmRequestId, String resolutionDetails, RequestStatus status) {
        String url = "http://localhost:8082/api/backoffice/" + crmRequestId + "/canceled?resolutionDetails=" + resolutionDetails + "&status=" + status;
        try {
            restTemplate.postForEntity(url, null, CustomerRequestBEDTO.class);
            logger.info("Successfully notified Back-End Office to cancel request with ID: " + crmRequestId);
        } catch (Exception e) {
            logger.error("Failed to notify Back-End Office to cancel request with ID: " + crmRequestId, e);
            throw new CRMNotificationException("Failed to notify Back-End Office to cancel request", e);
        }
    }

    /**
     * Notify Back-End Office to update the status of a CustomerRequest.
     *
     * @param crmRequestId The ID of the request to update.
     * @param status       The new status.
     */
    public void notifyStatusUpdate(Long crmRequestId, RequestStatus status) {
        String url = "http://localhost:8082/api/backoffice/" + crmRequestId + "/status";
        try {
            restTemplate.postForEntity(url + "?status=" + status.name(), null, CustomerRequestBEDTO.class);
            logger.info("Successfully notified Back-End Office to update status for request with ID: " + crmRequestId);
        } catch (Exception e) {
            logger.error("Failed to notify Back-End Office to update status for request with ID: " + crmRequestId, e);
            throw new CRMNotificationException("Failed to notify Back-End Office to update status", e);
        }
    }

    /**
     * Custom Exception for CRM notification failures.
     */
    public static class CRMNotificationException extends RuntimeException {
        public CRMNotificationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Interceptor to add Header-Identify in all requests.
     */
    private static class HeaderInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add("Header-Identify", "feo");
            return execution.execute(request, body);
        }
    }
}
