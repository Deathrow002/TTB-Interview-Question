package com.crm.feo.repository;

import com.crm.feo.model.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long> {
}
