import com.crm.feo.CRM_FEO_Application;
import com.crm.feo.model.CustomerRequest;
import com.crm.feo.model.RequestStatus;
import com.crm.feo.repository.CustomerRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

import java.util.List;

@SpringBootTest(classes = CRM_FEO_Application.class) // Ensure your main app class is specified
@AutoConfigureMockMvc
class FrontEndOfficeTestUnit {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRequestRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        CustomerRequest request1 = new CustomerRequest();
        request1.setCustomerName("Alice");
        request1.setIssueDetails("Issue 1");
        request1.setStatus(RequestStatus.PENDING);

        CustomerRequest request2 = new CustomerRequest();
        request2.setCustomerName("Bob");
        request2.setIssueDetails("Issue 2");
        request2.setStatus(RequestStatus.IN_PROGRESS);

        repository.saveAll(List.of(request1, request2));
    }

    @Test
    void shouldReturnAllRequests() throws Exception {
        mockMvc.perform(get("/api/customer-requests")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Alice")))
                .andExpect(content().string(containsString("Bob")));
    }

    @Test
    void shouldCreateNewRequest() throws Exception {
        String newRequestJson = """
                {
                    "customerName": "Charlie",
                    "issueDetails": "Issue 3"
                }
                """;

        mockMvc.perform(post("/api/customer-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Charlie"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}

