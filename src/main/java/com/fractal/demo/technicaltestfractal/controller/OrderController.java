package com.fractal.demo.technicaltestfractal.controller;

import com.fractal.demo.technicaltestfractal.dto.OrderDto;
import com.fractal.demo.technicaltestfractal.dto.ProductDto;
import com.fractal.demo.technicaltestfractal.dto.StatusEnum;
import com.fractal.demo.technicaltestfractal.dto.request.OrderRequest;
import com.fractal.demo.technicaltestfractal.dto.request.ProductRequest;
import com.fractal.demo.technicaltestfractal.exception.BussinessExcepcion;
import com.fractal.demo.technicaltestfractal.service.OrderService;
import com.fractal.demo.technicaltestfractal.util.JsonManagerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public Map<String, Object> getOrdersList(@RequestBody OrderRequest orderRequest){
        try{
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("result", orderService.getOrderList(orderRequest)).getResponse();
        } catch (Exception ex) {
            return JsonManagerResponse.processError(ex).getResponse();
        }
    }

    @PostMapping("/initOrder")
    public Map<String, Object> initOrder(@RequestBody OrderDto orderDto){

        Map<String, Object> response = null;
        try {
            orderDto.setStatus(StatusEnum.PENDING.getId());
            orderService.saveOrder(orderDto);
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }

    @PostMapping("/completeOrder")
    public Map<String, Object> completeOrder(@RequestBody OrderDto orderDto){

        Map<String, Object> response = null;
        try {
            orderDto.setStatus(StatusEnum.COMPLETED.getId());
            orderService.saveOrder(orderDto);
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }

    @PostMapping("/rejectOrder")
    public Map<String, Object> rejectOrder(@RequestBody OrderDto orderDto){

        Map<String, Object> response = null;
        try {
            orderService.rejectOrder(orderDto);
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }
}
