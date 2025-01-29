# TTB Interview Question

This repository contains various modules for the TTB interview question. Each module has its own Readme file with detailed information.

![TTB Interview Question](https://github.com/Deathrow002/TTB-Interview-Question/blob/main/CRM%20platform.png)

## Question Modules

1. **Algorithm and Problem Solving**
   - [Algorithm and Problem Solving](./AlgorithmAndProblemSolving/Readme.md)
   
2. **Practical Coding with Specific Technologies (CRUD operation)**
   - [CRM CRUD](./CRM_CRUD/Readme.md)
   
3. **Practical Coding with Specific Technologies (CRM platform)**
   - [CRM Microservices FEO](./CRM_Microservices_FEO/Readme.md)
   - [CRM Microservices BEO](./CRM_Microservices_BEO/Readme.md)

   **CRM platform Diagram**

![CRM Platform Diagram](https://github.com/Deathrow002/TTB-Interview-Question/blob/main/CRM%20platform.png)

4. **Problem Solving** 

   In case the developed API has performance issues, such as:

   - **API response is slower than expected**
     - Fix by: Optimize Database Queries if they are not prepared properly
     - Fix by: Cache the API responses and frequent database queries in memory
     - Fix by: Scaling up services vertically or horizontally

   - **The response from the database is delayed**
     - Fix by: Check database resource utilization
     - Fix by: Optimize Database Queries if they are not prepared properly
     - Fix by: Cache frequent database queries in memory
     - Fix by: Database Scaling

   - **High throughput API request (high workload)**
     - Fix by: Scaling up services vertically or horizontally
     - Fix by: Divide service if it is too big