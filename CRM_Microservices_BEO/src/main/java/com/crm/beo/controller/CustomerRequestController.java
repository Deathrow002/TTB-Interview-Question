package com.crm.beo.controller;

import com.crm.beo.model.CustomerRequest;
import com.crm.beo.model.RequestStatus;
import com.crm.beo.service.CustomerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/backoffice")
public class CustomerRequestController {

    private final CustomerRequestService customerRequestService;

    @Autowired
    public CustomerRequestController(CustomerRequestService customerRequestService) {
        this.customerRequestService = customerRequestService;
    }

    /**
     * Receives a new customer request.
     *
     * @param request The customer request to be created.
     * @return The saved customer request.
     */
    @PostMapping("/receive")
    public ResponseEntity<CustomerRequest> receiveRequest(
            @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerRequestService.receiveRequest(request));
    }

    /**
     * Retrieves a specific customer request by ID.
     *
     * @param crmRequestId The ID of the customer request.
     * @return The customer request, if found.
     */
    @GetMapping("/{crmRequestId}")
    public ResponseEntity<CustomerRequest> getRequest(
            @PathVariable Long crmRequestId) {
        return ResponseEntity.ok(customerRequestService.getRequest(crmRequestId));
    }

    /**
     * Retrieves all customer requests.
     *
     * @return A list of all customer requests.
     */
    @GetMapping()
    public ResponseEntity<List<CustomerRequest>> getAllRequests() {
        return ResponseEntity.ok(customerRequestService.getAllRequests());
    }

    /**
     * Updates the status of a customer request to IN_PROGRESS and assigns it to a user.
     *
     * @param crmRequestId        The ID of the customer request.
     * @param assignedTo The user assigned to handle the request.
     * @return The updated customer request.
     */
    @PostMapping("/{crmRequestId}/process")
    public ResponseEntity<CustomerRequest> processRequest(
            @RequestHeader("Header-Identify") String headerIdentify,
            @PathVariable Long crmRequestId,
            @RequestParam String assignedTo,
            @RequestParam(required = false) RequestStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status parameter is required.");
        }
        return ResponseEntity.ok(customerRequestService.updateStatus(crmRequestId, status, assignedTo, headerIdentify));
    }

    /**
     * Updates the status of a customer request to COMPLETED and provides resolution details.
     *
     * @param crmRequestId                The ID of the customer request.
     * @param resolutionDetails The resolution details of the request.
     * @return The updated customer request.
     */
    @PostMapping("/{crmRequestId}/complete")
    public ResponseEntity<CustomerRequest> completeRequest(
            @RequestHeader("Header-Identify") String headerIdentify,
            @PathVariable Long crmRequestId,
            @RequestParam String resolutionDetails,
            @RequestParam(required = false) RequestStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status parameter is required.");
        }
        return ResponseEntity.ok(customerRequestService.updateStatus(crmRequestId, status, resolutionDetails, headerIdentify));
    }

    /**
     * Updates the status of a customer request to CANCELED and provides cancellation details.
     *
     * @param crmRequestId                The ID of the customer request.
     * @param resolutionDetails The reason or details for cancellation.
     * @return The updated customer request.
     */
    @PostMapping("/{crmRequestId}/canceled")
    public ResponseEntity<CustomerRequest> cancelRequest(
            @RequestHeader("Header-Identify") String headerIdentify,
            @PathVariable Long crmRequestId,
            @RequestParam String resolutionDetails,
            @RequestParam(required = false) RequestStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status parameter is required.");
        }
        return ResponseEntity.ok(customerRequestService.updateStatus(crmRequestId, status, resolutionDetails, headerIdentify));
    }
}
