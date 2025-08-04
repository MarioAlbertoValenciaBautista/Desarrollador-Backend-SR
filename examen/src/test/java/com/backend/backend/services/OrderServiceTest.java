package com.backend.backend.services;

import com.backend.backend.models.OrderDTO;
import com.backend.backend.models.StatusEnum;
import com.backend.backend.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService();
        java.lang.reflect.Field field;
        try {
            field = OrderService.class.getDeclaredField("orderRepository");
            field.setAccessible(true);
            field.set(orderService, orderRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateOrder001() {
        Mockito.when(orderRepository.save(any(OrderDTO.class))).thenAnswer(invocation -> invocation.getArgument(0));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrigin("A");
        orderDTO.setDestination("B");
        OrderDTO result = orderService.createOrder(orderDTO);
        assertEquals(StatusEnum.CREATED.getStatus(), result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        verify(orderRepository, times(1)).save(any(OrderDTO.class));
    }

    @Test
    void testCreateOrder002() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrigin(null);
        orderDTO.setDestination("B");
        assertThrows(RuntimeException.class, () -> orderService.createOrder(orderDTO));
    }
    @Test
    void testCreateOrder003() {
        Mockito.when(orderRepository.save(any(OrderDTO.class))).thenAnswer(invocation -> invocation.getArgument(0));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrigin("A");
        orderDTO.setDestination("B");
        orderDTO.setStatus(StatusEnum.IN_TRANSIT.getStatus());
        OrderDTO auxOrderDTO = new OrderDTO();
        auxOrderDTO.setId(1L);
        auxOrderDTO.setOrigin("A");
        auxOrderDTO.setDestination("B");
        auxOrderDTO.setStatus(StatusEnum.CREATED.getStatus());
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(auxOrderDTO));
        OrderDTO result = orderService.createOrder(orderDTO);
        assertEquals(StatusEnum.IN_TRANSIT.getStatus(), result.getStatus());
    }

    @Test
    void testCreateOrder004() {
        Mockito.when(orderRepository.save(any(OrderDTO.class))).thenAnswer(invocation -> invocation.getArgument(0));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrigin("A");
        orderDTO.setDestination("B");
        orderDTO.setStatus(StatusEnum.DELIVERED.getStatus());
        OrderDTO auxOrderDTO = new OrderDTO();
        auxOrderDTO.setId(1L);
        auxOrderDTO.setOrigin("A");
        auxOrderDTO.setDestination("B");
        auxOrderDTO.setStatus(StatusEnum.IN_TRANSIT.getStatus());
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(auxOrderDTO));
        OrderDTO result = orderService.createOrder(orderDTO);
        assertEquals(StatusEnum.DELIVERED.getStatus(), result.getStatus());
    }

    //@Test
    void testCreateOrder005() {
        Mockito.when(orderRepository.save(any(OrderDTO.class))).thenAnswer(invocation -> invocation.getArgument(0));
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrigin("A");
        orderDTO.setDestination("B");
        orderDTO.setStatus(StatusEnum.DELIVERED.getStatus());OrderDTO auxOrderDTO = new OrderDTO();
        auxOrderDTO.setId(1L);
        auxOrderDTO.setOrigin("A");
        auxOrderDTO.setDestination("B");
        auxOrderDTO.setStatus(StatusEnum.DELIVERED.getStatus());
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(auxOrderDTO));
        OrderDTO result = orderService.createOrder(orderDTO);
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(orderDTO));
    }

    @Test
    void testGetOrder001() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderDTO));
        OrderDTO result = orderService.getOrder(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetOrder002() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());
        OrderDTO result = orderService.getOrder(2L);
        assertNull(result);
    }

    @Test
    void testListOrders001() {
        ArrayList<OrderDTO> orders = new ArrayList<>();
        orders.add(new OrderDTO());
        LocalDateTime date = LocalDateTime.parse("2024-06-10T15:30:00");
        Mockito.when(orderRepository.findByFilters("CREATED", "A", "B", date, date)).thenReturn(orders);
        ArrayList<OrderDTO> result = orderService.listOrders("CREATED", "A", "B", date, date);
        assertEquals(1, result.size());
    }
}