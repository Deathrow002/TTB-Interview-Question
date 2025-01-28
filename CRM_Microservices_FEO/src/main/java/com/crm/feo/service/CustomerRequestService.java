package com.crm.feo.service;

import com.crm.feo.model.CustomerRequest;
import com.crm.feo.model.RequestStatus;
import com.crm.feo.repository.CustomerRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRequestService {

    private final CustomerRequestRepository repository;

    @Autowired
    private final CRMNotifierService crmNotifierService;

    public CustomerRequestService(CustomerRequestRepository repository, CRMNotifierService crmNotifierService) {
        this.repository = repository;
        this.crmNotifierService = crmNotifierService;
    }

    public CustomerRequest createRequest(CustomerRequest request) {
        request.setStatus(RequestStatus.PENDING);
        CustomerRequest payload = repository.save(request);
        crmNotifierService.notifyReceive(payload);
        return payload;
    }

    public List<CustomerRequest> getAllRequests() {
        return repository.findAll();
    }

    public CustomerRequest getRequest(Long requestId) {
        return repository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request with ID " + requestId + " not found"));
    }

    public CustomerRequest cancelRequest(Long requestId, String headerIdentify) {
        try {
            // Call Notifier Service to Trigger Back-End Office to change Request Status to CANCELED
            CustomerRequest payload = updateStatus(requestId, RequestStatus.CANCELED);
            if (headerIdentify.equals("feo")) {
                crmNotifierService.notifyCancel(payload.getId(), payload.getIssueDetails(), payload.getStatus());
            }
            return payload;
        } catch (Exception e) {
            System.err.println("Failed to notify CRM: " + e.getMessage());
            return null;
        }
    }

    public CustomerRequest updateStatus(Long requestId, RequestStatus status) {
        CustomerRequest request = repository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request with ID " + requestId + " not found"));

        request.setStatus(status);

        return repository.save(request);
    }

    public static class RequestNotFoundException extends RuntimeException {
        public RequestNotFoundException(String message) {
            super(message);
        }
    }
}


