import com.crm.beo.CRM_BEO_Application;
import com.crm.beo.model.CustomerRequest;
import com.crm.beo.model.RequestStatus;
import com.crm.beo.service.CustomerRequestService;
import com.crm.beo.controller.CustomerRequestController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CRM_BEO_Application.class) // Ensure your main app class is specified
@AutoConfigureMockMvc
class BackEndOfficeTestUnit {

    private MockMvc mockMvc;

    @Mock
    private CustomerRequestService customerRequestService;

    @InjectMocks
    private CustomerRequestController customerRequestController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerRequestController).build();
    }

    @Test
    void testReceiveRequest() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setCrmRequestId(1L);
        request.setAssignedTo("John Doe");
        request.setResolutionDetails("Details of the request.");
        request.setStatus(RequestStatus.PENDING);

        when(customerRequestService.receiveRequest(any(CustomerRequest.class))).thenReturn(request);

        mockMvc.perform(post("/api/backoffice/receive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"assignedTo\": \"John Doe\", \"resolutionDetails\": \"Details of the request.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crmRequestId").value(1))
                .andExpect(jsonPath("$.assignedTo").value("John Doe"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(customerRequestService, times(1)).receiveRequest(any(CustomerRequest.class));
    }

    @Test
    void testGetRequest() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setCrmRequestId(1L);
        request.setAssignedTo("John Doe");
        request.setResolutionDetails("Details of the request.");
        request.setStatus(RequestStatus.PENDING);

        when(customerRequestService.getRequest(1L)).thenReturn(request);

        mockMvc.perform(get("/api/backoffice/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crmRequestId").value(1))
                .andExpect(jsonPath("$.assignedTo").value("John Doe"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(customerRequestService, times(1)).getRequest(1L);
    }

    @Test
    void testGetAllRequests() throws Exception {
        CustomerRequest request1 = new CustomerRequest();
        request1.setCrmRequestId(1L);
        request1.setAssignedTo("John Doe");
        request1.setResolutionDetails("Details of the request.");
        request1.setStatus(RequestStatus.PENDING);

        CustomerRequest request2 = new CustomerRequest();
        request2.setCrmRequestId(2L);
        request2.setAssignedTo("Jane Doe");
        request2.setResolutionDetails("Details of another request.");
        request2.setStatus(RequestStatus.COMPLETED);

        List<CustomerRequest> requests = Arrays.asList(request1, request2);

        when(customerRequestService.getAllRequests()).thenReturn(requests);

        mockMvc.perform(get("/api/backoffice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].crmRequestId").value(1))
                .andExpect(jsonPath("$[0].assignedTo").value("John Doe"))
                .andExpect(jsonPath("$[1].crmRequestId").value(2))
                .andExpect(jsonPath("$[1].assignedTo").value("Jane Doe"));

        verify(customerRequestService, times(1)).getAllRequests();
    }

    @Test
    void testProcessRequest() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setCrmRequestId(1L);
        request.setAssignedTo("John Doe");
        request.setResolutionDetails("Details of the request.");
        request.setStatus(RequestStatus.IN_PROGRESS);

        when(customerRequestService.updateStatus(eq(1L), eq(RequestStatus.IN_PROGRESS), eq("John Doe"), anyString()))
                .thenReturn(request);

        mockMvc.perform(post("/api/backoffice/1/process")
                        .header("Header-Identify", "beo")
                        .param("assignedTo", "John Doe")
                        .param("status", "IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crmRequestId").value(1))
                .andExpect(jsonPath("$.assignedTo").value("John Doe"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        verify(customerRequestService, times(1)).updateStatus(eq(1L), eq(RequestStatus.IN_PROGRESS), eq("John Doe"), anyString());
    }

    @Test
    void testCompleteRequest() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setCrmRequestId(1L);
        request.setAssignedTo("John Doe");
        request.setResolutionDetails("Completed the request.");
        request.setStatus(RequestStatus.COMPLETED);

        when(customerRequestService.updateStatus(eq(1L), eq(RequestStatus.COMPLETED), eq("Completed the request."), anyString()))
                .thenReturn(request);

        mockMvc.perform(post("/api/backoffice/1/complete")
                        .header("Header-Identify", "beo")
                        .param("resolutionDetails", "Completed the request.")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crmRequestId").value(1))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(customerRequestService, times(1)).updateStatus(eq(1L), eq(RequestStatus.COMPLETED), eq("Completed the request."), anyString());
    }

    @Test
    void testCancelRequest() throws Exception {
        CustomerRequest request = new CustomerRequest();
        request.setCrmRequestId(1L);
        request.setAssignedTo("John Doe");
        request.setResolutionDetails("Request canceled.");
        request.setStatus(RequestStatus.CANCELED);

        when(customerRequestService.updateStatus(eq(1L), eq(RequestStatus.CANCELED), eq("Request canceled."), anyString()))
                .thenReturn(request);

        mockMvc.perform(post("/api/backoffice/1/canceled")
                        .header("Header-Identify", "beo")
                        .param("resolutionDetails", "Request canceled.")
                        .param("status", "CANCELED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crmRequestId").value(1))
                .andExpect(jsonPath("$.status").value("CANCELED"));

        verify(customerRequestService, times(1)).updateStatus(eq(1L), eq(RequestStatus.CANCELED), eq("Request canceled."), anyString());
    }
}
