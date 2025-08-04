package com.backend.backend.controllers;

import com.backend.backend.models.DriverDTO;
import com.backend.backend.repositories.DriverRepository;
import com.backend.backend.services.DriverService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @Test
    public void testCreateDriver001() throws Exception {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setName("Driver");
        driverDTO.setLicenseNumber("123456789");
        driverDTO.setActive(true);

        Mockito.when(driverService.createDriver(Mockito.any(DriverDTO.class))).thenReturn(driverDTO);
        mockMvc.perform(post("/driver")
                .contentType("application/json")
                .content("{\"name\":\"Driver\",\"licenseNumber\":\"123456789\",\"active\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Driver"))
                .andExpect(jsonPath("$.licenseNumber").value("123456789"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void testListDrivers001() throws Exception {
        DriverDTO driver1 = new DriverDTO();
        driver1.setName("Juan");
        DriverDTO driver2 = new DriverDTO();
        driver2.setName("Pedro");
        Mockito.when(driverService.listDrivers()).thenReturn(Arrays.asList(driver1, driver2));

        mockMvc.perform(get("/driver/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[1].name").value("Pedro"));
    }
}
