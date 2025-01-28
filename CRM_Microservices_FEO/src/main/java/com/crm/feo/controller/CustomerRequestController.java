package com.crm.feo.controller;

import com.crm.feo.model.CustomerRequest;
import com.crm.feo.model.RequestStatus;
import com.crm.feo.service.CustomerRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-requests")
public class CustomerRequestController {

    private final CustomerRequestService service;

    public CustomerRequestController(CustomerRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustomerRequest> createRequest(@RequestBody CustomerRequest request) {
        CustomerRequest createdRequest = service.createRequest(request);
        return ResponseEntity.ok(createdRequest);
    }

    @GetMapping
    public ResponseEntity<?> getRequests(@RequestParam(value = "id", required = false) Long requestId) {
        if (requestId == null) {
            List<CustomerRequest> requests = service.getAllRequests();
            return ResponseEntity.ok(requests);
        } else {
            CustomerRequest request = service.getRequest(requestId);
            return ResponseEntity.ok(request);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<CustomerRequest> cancelRequest(
            @RequestHeader("Header-Identify") String headerIdentify,
            @PathVariable("id") Long requestId) {
        CustomerRequest canceledRequest = service.cancelRequest(requestId, headerIdentify);

        return ResponseEntity.ok(canceledRequest);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CustomerRequest> updateStatus(
            @PathVariable("id") Long requestId,
            @RequestParam("status") RequestStatus status) {
        CustomerRequest updatedRequest = service.updateStatus(requestId, status);
        return ResponseEntity.ok(updatedRequest);
    }
}

