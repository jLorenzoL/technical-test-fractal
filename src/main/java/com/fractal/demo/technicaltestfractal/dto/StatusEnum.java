package com.fractal.demo.technicaltestfractal.dto;

import java.util.Arrays;

public enum StatusEnum {

    PENDING(1, "PENDING"),
    COMPLETED(2,"COMPLETED"),
    REJECTED(3,"REJECTED");

    private Integer id;
    private String description;

    StatusEnum(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
