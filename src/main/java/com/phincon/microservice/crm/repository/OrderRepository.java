package com.phincon.microservice.crm.repository;

import com.phincon.microservice.crm.model.Orders;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface OrderRepository extends R2dbcRepository<Orders, String> {


}
