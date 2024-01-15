package com.phincon.microservice.crm.service;

import com.phincon.microservice.crm.model.Orders;
import com.phincon.microservice.crm.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }



    @JmsListener(destination = "queue.crm.register")
    public void receiveMessage(Message<Orders> orderGet) {
        log.info("Received <" + orderGet + ">");
        createOrder(orderGet.getPayload()).subscribe();
    }

    @Override
    public Mono<Orders> createOrder(Orders order) {
        Orders orderNew = new Orders();
        orderNew.setId(order.getId());
        orderNew.setProductId(order.getProductId());
        orderNew.setStatus(order.getStatus());
        orderNew.setPrice(order.getPrice());
        Mono<Orders> save = orderRepository.save(orderNew);
        log.info("Order saved: " + save);
        return save;
    }
}
