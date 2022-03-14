package com.fractal.demo.technicaltestfractal.repository;

import com.fractal.demo.technicaltestfractal.dto.OrderDto;
import com.fractal.demo.technicaltestfractal.dto.request.OrderRequest;
import com.fractal.demo.technicaltestfractal.entity.OrderEntity;
import com.fractal.demo.technicaltestfractal.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public void saveOrder(OrderEntity orderEntity){

        mongoTemplate.save(orderEntity);

    }

    public void rejectOrder(OrderDto orderDto){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(orderDto.getId()));
        Update update = new Update();
        update.set("status", orderDto.getStatus());
        mongoTemplate.updateFirst(query, update, OrderEntity.class);
    }

    public Page<OrderEntity> getOrdersByPagination(Query query, Pageable pageable){

        List<OrderEntity> list = getOrders(query);

        if(list.isEmpty()){
            return null;
        }

        long count = mongoTemplate.count(new Query(), OrderEntity.class);
        Page<OrderEntity> resultPage = new PageImpl<>(list, pageable, count);
        return resultPage;
    }

    public List<OrderEntity> getOrders(Query query){
        return mongoTemplate.find(query,OrderEntity.class);
    }
}
