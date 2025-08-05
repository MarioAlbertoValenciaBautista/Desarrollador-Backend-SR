package com.backend.backend.controllers;

import com.backend.backend.models.AssignDTO;
import com.backend.backend.services.AssignService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssignController.class)
public class AssignControllerTest {

    @MockBean
    private AssignService assignService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void testAssignDriver001() throws Exception {
        Long orderId = 1L;
        Long driverId = 1L;
        AssignDTO assignDTO = new AssignDTO();
        assignDTO.setOrderId(orderId);
        assignDTO.setDriverId(driverId);
        assignDTO.setDriverName("Test Driver");
        Mockito.when(assignService.assignDriverToOrder(orderId, driverId))
                .thenReturn(assignDTO);
        mockMvc.perform(post("/assign")
                        .param("orderId", orderId.toString())
                        .param("driverId", driverId.toString()))
                .andExpect(status().isOk());
    }
}
