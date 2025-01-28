package com.crm.feo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
@ComponentScan(basePackages = "com.crm.feo")
public class CRM_FEO_Application {
    public static void main(String[] args) {
        SpringApplication.run(CRM_FEO_Application.class, args);
    }
}