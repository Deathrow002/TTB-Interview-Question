package com.crm.controller;

import com.crm.model.dto.CustomerSalesDTO;
import com.crm.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    // Endpoint to retrieve top sales for each customer in the past year
    @GetMapping("/top-sales")
    public List<CustomerSalesDTO> getTopSalesForEachCustomerInPastYear() {
        return saleService.getAllSalesByCustomerId();
    }
}
