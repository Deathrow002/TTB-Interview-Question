package com.crm.beo.service;

import com.crm.beo.model.CustomerRequest;
import com.crm.beo.model.RequestStatus;
import com.crm.beo.repository.CustomerRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRequestService {

    private final CustomerRequestRepository repository;
    private final CRMNotifierService crmNotifierService;

    @Autowired
    public CustomerRequestService(CustomerRequestRepository repository, CRMNotifierService crmNotifierService) {
        this.repository = repository;
        this.crmNotifierService = crmNotifierService;
    }

    /**
     * Receives a new customer request, sets its initial status to PENDING, and saves it.
     *
     * @param request The incoming customer request.
     * @return The saved customer request with a generated ID.
     */
    public CustomerRequest receiveRequest(CustomerRequest request) {
        request.setStatus(RequestStatus.PENDING);
        return repository.save(request);
    }

    /**
     * Updates the status of a customer request. Additional details are updated based on the status.
     *
     * @param crmRequestId      The ID of the request to update.
     * @param status  The new status of the request.
     * @param details Additional details (e.g., assigned agent or resolution details).
     * @param headerIdentify The value of the Header-Identify header.
     * @return The updated customer request.
     */
    public CustomerRequest updateStatus(Long crmRequestId, RequestStatus status, String details, String headerIdentify) {
        if (status == RequestStatus.PENDING) {
            throw new IllegalArgumentException("Status cannot be updated to PENDING.");
        }

        CustomerRequest request = repository.findById(crmRequestId)
                .orElseThrow(() -> new RequestNotFoundException("Request with CRM ID " + crmRequestId + " not found"));

        request.setStatus(status);

        if (status == RequestStatus.IN_PROGRESS) {
            request.setAssignedTo(details);
        } else if (status == RequestStatus.COMPLETED || status == RequestStatus.CANCELED) {
            request.setResolutionDetails(details);
        }

        if (headerIdentify.equals("beo")) {
            crmNotifierService.notifyCRM(request.getCrmRequestId(), request.getStatus());
        }
        return repository.save(request);
    }

    /**
     * Retrieves a single customer request by its ID.
     *
     * @param crmRequestId The ID of the request to retrieve.
     * @return The customer request, if found.
     */
    public CustomerRequest getRequest(Long crmRequestId) {
        return repository.findById(crmRequestId)
                .orElseThrow(() -> new RequestNotFoundException("Request with CRM ID " + crmRequestId + " not found"));
    }

    /**
     * Retrieves all customer requests.
     *
     * @return A list of all customer requests.
     */
    public List<CustomerRequest> getAllRequests() {
        return repository.findAll();
    }

    /**
     * Custom exception for handling cases where a request is not found.
     */
    public static class RequestNotFoundException extends RuntimeException {
        public RequestNotFoundException(String message) {
            super(message);
        }
    }
}
