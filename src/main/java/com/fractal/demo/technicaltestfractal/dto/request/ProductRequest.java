package com.fractal.demo.technicaltestfractal.dto.request;

import lombok.Data;

@Data
public class ProductRequest {

    private Integer maxResults;
    private Integer page;
    private boolean paginate;
}
