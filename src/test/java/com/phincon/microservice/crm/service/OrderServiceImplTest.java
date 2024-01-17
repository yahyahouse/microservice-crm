package com.phincon.microservice.crm.service;

import com.phincon.microservice.crm.model.Orders;
import com.phincon.microservice.crm.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jms.core.JmsTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    JmsTemplate jmsTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void receiveMessage() {

    }

    @Test
    void createOrder() {
        Orders order = new Orders();
        order.setId("123");
        order.setProductId("456");
        order.setStatus("Pending");
        order.setPrice(100L);
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.save(any(Orders.class))).thenReturn(Mono.just(order));
        OrderService orderService = new OrderServiceImpl();
        Mono<Orders> result = orderService.createOrder(order);
        StepVerifier.create(result)
                .expectNext(order)
                .verifyComplete();
        verify(orderRepository, times(1)).save(any(Orders.class));
    }
}