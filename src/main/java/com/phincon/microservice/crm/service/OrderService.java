package com.phincon.microservice.crm.service;


import com.phincon.microservice.crm.model.Orders;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<Orders> createOrder(Orders order);

}
