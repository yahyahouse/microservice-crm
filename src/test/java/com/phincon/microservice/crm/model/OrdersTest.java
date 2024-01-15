package com.phincon.microservice.crm.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrdersTest {

    @Test
    void ordersTest() {
        Orders orders = new Orders();
        orders.setId("1");
        orders.setProductId("1");
        orders.setStatus("status");
        orders.setPrice(1L);
        orders.setActionId("1");
        assertEquals("1", orders.getId());
        assertEquals("1", orders.getProductId());
        assertEquals("status", orders.getStatus());
        assertEquals(1L, orders.getPrice());
        assertEquals("1", orders.getActionId());

    }

}