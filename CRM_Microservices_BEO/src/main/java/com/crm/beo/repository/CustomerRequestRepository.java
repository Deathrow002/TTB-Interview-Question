package com.crm.beo.repository;

import com.crm.beo.model.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long> {}

