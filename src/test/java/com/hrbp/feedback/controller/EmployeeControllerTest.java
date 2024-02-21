package com.hrbp.feedback.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.service.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController controller;

    @Test
    public void testFindEmployeeDetails_WithEmployeeFound() throws Exception {
        Integer employeeId = 1;

        // Mocking behavior of employeeService.findEmployeeDtoById when an employee is found
        when(employeeService.findEmployeeDtoById(employeeId))
                .thenReturn(Optional.of(createMockEmployeeDTO()));

        mockMvc.perform(get("/employeeDetails/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public EmployeeDTO createMockEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setRole(null);
        employeeDTO.setDepartment(null);
        employeeDTO.setManager(null);
        employeeDTO.setBuHead(null);
        employeeDTO.setHireDate(new Date());
        employeeDTO.setFeedbacks(null);

        return employeeDTO;
    }
}
