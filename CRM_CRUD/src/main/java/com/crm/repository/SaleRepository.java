package com.crm.repository;

import com.crm.model.Sale;
import com.crm.model.dto.CustomerSalesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {

    // Custom query to retrieve highest sales per customer in the past year, sorted by sales
    // Native SQL query to retrieve highest sales per customer in the past year, sorted by sales
    @Query(value = "SELECT CAST(s.customer_id AS UNIQUEIDENTIFIER) AS customer_id, " +
            "SUM(s.sale_amount) AS total_sales, " +
            "DENSE_RANK() OVER (ORDER BY SUM(s.sale_amount) DESC) AS rank " +
            "FROM sales s " +
//            "WHERE s.sale_date >= DATEADD(YEAR, -1, GETDATE()) " +  // SQL Server example
            "GROUP BY s.customer_id " +
            "ORDER BY total_sales DESC",
            nativeQuery = true)
    List<Object[]> findTopSalesRanks();

}
