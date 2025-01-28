package com.crm.service;

import com.crm.model.Customer;
import com.crm.model.dto.CustomerDTO;
import com.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get Customer by ID
    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    // Add a new Customer
    public void addCustomer(CustomerDTO customerDTO) {
        LocalDateTime now = LocalDateTime.now();
        Customer customer = mapToEntity(customerDTO, now, now);
        customerRepository.save(customer);
    }

    // Update an existing Customer
    public void updateCustomer(CustomerDTO customerDTO) {
        LocalDateTime now = LocalDateTime.now();

        // Check if the customer exists before updating
        Customer existingCustomer = customerRepository.findById(customerDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerDTO.getCustomerId()));

        Customer updatedCustomer = mapToEntity(customerDTO, existingCustomer.getCreatedOn(), now);
        customerRepository.save(updatedCustomer);
    }

    // Utility method to map DTO to Entity
    private Customer mapToEntity(CustomerDTO dto, LocalDateTime createdOn, LocalDateTime modifiedOn) {
        return new Customer(
                dto.getCustomerId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getCustomerDate(),
                dto.getIsVIP(),
                dto.getStatusCode(),
                createdOn,
                modifiedOn
        );
    }
}
