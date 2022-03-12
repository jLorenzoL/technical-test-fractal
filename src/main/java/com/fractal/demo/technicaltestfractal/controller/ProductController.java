package com.fractal.demo.technicaltestfractal.controller;

import com.fractal.demo.technicaltestfractal.dto.ProductDto;
import com.fractal.demo.technicaltestfractal.dto.request.ProductRequest;
import com.fractal.demo.technicaltestfractal.exception.BussinessExcepcion;
import com.fractal.demo.technicaltestfractal.service.ProductService;
import com.fractal.demo.technicaltestfractal.util.JsonManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/saveProduct")
    public Map<String, Object> saveProduct(@RequestBody ProductDto productDto){

        Map<String, Object> response = null;
        try {
            productService.saveProduct(productDto);
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }

    @GetMapping("/products")
    public Map<String, Object> getProductList(@RequestBody ProductRequest productRequest){
        try{
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("products", productService.getProductList(productRequest)).getResponse();
        } catch (Exception ex) {
            return JsonManagerResponse.processError(ex).getResponse();
        }
    }

    @PostMapping("/update")
    public Map<String, Object> updateProduct(@RequestBody ProductDto productDto){
        Map<String, Object> response = null;
        try{
            productService.updateProductById(productDto);
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (Exception ex){
            response = new JsonManagerResponse(ex.getMessage(), Boolean.FALSE).getResponse();
        }

        return response;
    }

    @PostMapping("/delete")
    public Map<String, Object> deleteProduct(@RequestBody ProductDto productDto){
        Map<String, Object> response = null;
        try{
            productService.deleteProduct(productDto.getId());
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (Exception ex){
            response = new JsonManagerResponse(ex.getMessage(), Boolean.FALSE).getResponse();
        }

        return response;
    }
}