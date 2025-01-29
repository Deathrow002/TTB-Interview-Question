package com.crm.service;
import com.crm.model.Customer;
import com.crm.model.Sale;
import com.crm.repository.CustomerRepository;
import com.crm.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
public class DataGeneratorApplication {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SaleRepository saleRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataGeneratorApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                // Create a Customer
                Customer customer = new Customer();
                customer.setFirstName("FirstName" + i);
                customer.setLastName("LastName" + i);
                customer.setCustomerDate(LocalDate.of(2020, 1 + random.nextInt(12), 1 + random.nextInt(28)));
                customer.setIsVIP(random.nextBoolean());
                customer.setStatusCode("ACTIVE");

                // Save the customer into the database
                customerRepository.save(customer);

                // Create a Sale
                Sale sale = new Sale();
                sale.setCustomerId(customer.getCustomerId()); // Use the generated customer ID
                sale.setSaleAmount(BigDecimal.valueOf(random.nextInt(1000) + 1)); // Random sale amount between 1 and 1000
                sale.setSaleDate(Timestamp.valueOf(LocalDateTime.now().minusDays(random.nextInt(365)))); // Random sale date within last year

                // Save the sale into the database
                saleRepository.save(sale);
            }
            System.out.println("Generated 1000 sample customers and sales.");
        };
    }
}


