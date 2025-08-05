package com.backend.backend.controllers;

import com.backend.backend.models.OrderDTO;
import com.backend.backend.services.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;

    // Test for the createOrder method in OrderController
    @Test
    void testCreateOrder001() throws Exception {
        Mockito.when(orderService.createOrder(Mockito.any(OrderDTO.class))).thenReturn(new OrderDTO());
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"origin\":\"Origin\",\"destination\":\"Destination\"}"))
                .andExpect(status().isOk());
    }
    @Test
    void testCreateOrder002() throws Exception {
        Mockito.when(orderService.createOrder(Mockito.any(OrderDTO.class))).thenReturn(new OrderDTO());
        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"A\",\"status\":\"NEW\",\"origin\":\"Origin\",\"destination\":\"Destination\"}"))
                .andExpect(status().is5xxServerError());
    }

    // Test for the getOrder method in OrderController
    @Test
    void testGetOrder001() throws Exception {
        Mockito.when(orderService.getOrder(Mockito.anyLong())).thenReturn(new OrderDTO());
        mockMvc.perform(get("/order/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void testGetOrder002() throws Exception {
        Mockito.when(orderService.getOrder(Mockito.anyLong())).thenReturn(new OrderDTO());
        mockMvc.perform(get("/order/A")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
    @Test
    void testGetOrder003() throws Exception {
        Mockito.when(orderService.getOrder(Mockito.anyLong())).thenReturn(new OrderDTO());
        mockMvc.perform(get("/order/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    // Test for the listOrders method in OrderController
    @Test
    void testListOrders001() throws Exception {
        ArrayList<OrderDTO> orderDTOs = new ArrayList<>();
        orderDTOs.add(new OrderDTO());
        Mockito.when(orderService.listOrders(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(orderDTOs);
        mockMvc.perform(get("/order")
                        .param("status","CREATED")
                        .param("origin", "A")
                        .param("destination", "B"))
                .andExpect(status().isOk());
    }
}
