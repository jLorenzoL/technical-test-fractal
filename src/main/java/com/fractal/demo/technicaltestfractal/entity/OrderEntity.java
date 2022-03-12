package com.fractal.demo.technicaltestfractal.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "coll_order")
public class OrderEntity {

    private String id;

    private LocalDateTime date;

    private String customer;

    @Field(value = "tax_amount")
    private Double taxAmount;


}
