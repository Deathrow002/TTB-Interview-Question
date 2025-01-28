package com.crm.service;

import com.crm.model.dto.CustomerSalesDTO;
import com.crm.repository.CustomerRepository;
import com.crm.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private final SaleRepository saleRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    public SaleService(SaleRepository saleRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
    }

    // Find Sales by Customer ID
    public List<CustomerSalesDTO> getAllSalesByCustomerId() {
        List<Object[]> objects = saleRepository.findTopSalesRanks();  // Get raw results from repository
        List<CustomerSalesDTO> payload = new ArrayList<>();  // Prepare list to hold CustomerSalesDTO

        // Iterate over raw results and map them to CustomerSalesDTO
        for (Object[] obj : objects) {
            CustomerSalesDTO customerSalesDTO = new CustomerSalesDTO(
                    (String) obj[0],  // Cast customer_id to String
                    (BigDecimal) obj[1],  // Cast total_sales to BigDecimal
                    ((Long) obj[2]).intValue()  // Cast rank to Integer
            );
            payload.add(customerSalesDTO);  // Add the mapped DTO to the list
        }

        return payload;  // Return the list of CustomerSalesDTO
    }
}
