package com.phincon.microservice.crm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Orders {

    @Getter
    @Setter
    @Id
    String id;
    String productId;
    String status;
    Long price;
    String actionId;

}
