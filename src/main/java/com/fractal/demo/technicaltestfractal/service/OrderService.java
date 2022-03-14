package com.fractal.demo.technicaltestfractal.service;

import com.fractal.demo.technicaltestfractal.dto.OrderDto;
import com.fractal.demo.technicaltestfractal.dto.ProductDto;
import com.fractal.demo.technicaltestfractal.dto.StatusEnum;
import com.fractal.demo.technicaltestfractal.dto.request.OrderRequest;
import com.fractal.demo.technicaltestfractal.entity.OrderEntity;
import com.fractal.demo.technicaltestfractal.entity.ProductEntity;
import com.fractal.demo.technicaltestfractal.exception.BussinessExcepcion;
import com.fractal.demo.technicaltestfractal.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    List<ProductEntity> productEntityList;

    public void saveOrder(OrderDto orderDto){

        OrderEntity orderEntity = new OrderEntity();

        try{
            orderEntity.setDate(orderDto.getDate());
            orderEntity.setCustomer(orderDto.getCostumer());
            orderEntity.setQuantity(orderDto.getQuantity());
            orderEntity.setStatus(orderDto.getStatus());
            validateProductList(orderDto.getListProducts());
            //orderEntity.setListProductEntity(productEntityList);
            orderEntity.setSubtotal(orderDto.getSubtotal());
            orderEntity.setTaxAmount(orderDto.getTaxAmount());
            orderEntity.setTotalAmount(orderDto.getTotalAmount());

            orderRepository.saveOrder(orderEntity);
        }catch (Exception ex) {
            throw new BussinessExcepcion("An error occures during product resgitration", ex);
        }
    }

    public void rejectOrder(OrderDto orderDto){
        try {
            orderDto.setStatus(StatusEnum.REJECTED.getId());
            orderRepository.rejectOrder(orderDto);
        } catch (Exception ex) {
            throw new BussinessExcepcion(ex.getMessage());
        }
    }

    private void validateProductList(List<ProductDto> listProducts){

        if(listProducts.size()>0){
            listProducts.forEach(p->{
                ProductEntity productEntity = new ProductEntity();
                productEntity.setName(p.getName());
                productEntity.setUnitPrice(p.getUnitPrice());
                productEntityList.add(productEntity);
            });
        }

    }

    public Map<String, Object> getOrderList(OrderRequest orderRequest){

        Map<String,Object> resultMap = new HashMap<>();
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "date");
            Pageable pageable = PageRequest.of(orderRequest.getPage(), orderRequest.getMaxResults(), sort);
            Query query = new Query().with(pageable);

            List<OrderEntity> orderEntityList = new ArrayList<>();
            if(orderRequest.isPaginate()){
                Page<OrderEntity> orderPage = orderRepository.getOrdersByPagination(query, pageable);
                long elements = 0L;
                if(Objects.nonNull(orderPage)){
                    orderEntityList = orderPage.getContent();
                    elements = orderPage.getTotalElements();
                    resultMap.put("totalPages", orderPage.getTotalPages());
                }
                resultMap.put("totalElements", elements);
            }
            else {
                orderEntityList = orderRepository.getOrders(query);
            }
            resultMap.put("orders", orderEntityList);
        } catch (Exception ex) {
            throw new BussinessExcepcion("Something happened during query.");
        }

        return resultMap;

    }


}
