package com.fractal.demo.technicaltestfractal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "coll_product")
public class ProductEntity {

    @Id
    private String id;

    @Indexed
    private String name;

    private Double unitPrice;

    private boolean state;

    private Integer category;

}
