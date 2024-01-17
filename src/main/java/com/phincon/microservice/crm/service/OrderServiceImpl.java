package com.phincon.microservice.crm.service;

import com.phincon.microservice.crm.model.Orders;
import com.phincon.microservice.crm.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    JmsTemplate jmsTemplate;

    private final Set<String> sentOrders = new HashSet<>();



    @JmsListener(destination = "queue.crm.register")
    public void receiveMessage(Message<Orders> orderGet) {
        log.info("Received <" + orderGet + ">");
        createOrder(orderGet.getPayload()).subscribe();
    }
    @Override
    public Mono<Orders> createOrder(Orders order) {
        try {
            order.setStatus("Completed");
            Mono<Orders> save = orderRepository.save(order);
            jmsTemplate.convertAndSend("queue.status", "Success");
            log.info("Order saved: " + save);
            return save;
        }catch (Exception e) {
            log.info("Order failed: " + e.getMessage());
            jmsTemplate.convertAndSend("queue.status", "Failed");
            jmsTemplate.convertAndSend("queue.crm.register", order);
            return Mono.just(order);
        }

    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void getOrders() {
        Flux<Orders> ordersFlux = orderRepository.findAll();
        ordersFlux.filter(order -> !sentOrders.contains(order.getId()))
                .doOnNext(order -> {
                    log.info("Sending order " + order.getId());
                    jmsTemplate.convertAndSend("queue.all.order", order);
                    sentOrders.add(order.getId());
                })
                .subscribe();
    }
}
