package com.fractal.demo.technicaltestfractal.dto;

import java.util.Arrays;
import java.util.List;

public enum CategoryEnum {

    COOKIES("COOKIE"),
    CANDIES("CANDIES"),
    CAKES("CAKES"),
    DESSERTS("DESSERTS"),
    DRINKS("DRINKS");

    private final String description;

    CategoryEnum(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List listCategory(){
        return Arrays.asList(CategoryEnum.values());
    }
}
