package com.fractal.demo.technicaltestfractal.repository;

import com.fractal.demo.technicaltestfractal.dto.ProductDto;
import com.fractal.demo.technicaltestfractal.dto.request.ProductRequest;
import com.fractal.demo.technicaltestfractal.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public void saveProduct(ProductEntity productEntity){

        mongoTemplate.save(productEntity);

    }

    public List<ProductEntity> getListProduct(ProductRequest productRequest){

        Query query = new Query();
        query.limit(productRequest.getMaxResults());
        query.skip((productRequest.getPage() - 1) * productRequest.getMaxResults());
        return  mongoTemplate.find(query, ProductEntity.class);
    }

    @Transactional
    public void updateInfoProduct(ProductDto productDto){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(productDto.getId()));
        Update update = new Update();
        update.set("name", productDto.getName());
        update.set("unitPrice", productDto.getUnitPrice());
        update.set("state", productDto.isState());
        update.set("category", productDto.getCategory());
        mongoTemplate.updateFirst(query, update, ProductEntity.class);

    }

    public void deleteProduct(String id){
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.findAllAndRemove(query, ProductEntity.class);
    }

    public ProductEntity getProductInfo(String id){
        return mongoTemplate.findById(id, ProductEntity.class);
    }

    public Page<ProductEntity> getProductsByPagination(Query query, Pageable pageable){

        List<ProductEntity> list = getProducts(query);

        if(list.isEmpty()){
            return null;
        }

        long count = mongoTemplate.count(new Query(), ProductEntity.class);
        Page<ProductEntity> resultPage = new PageImpl<>(list, pageable, count);
        return resultPage;
    }

    public List<ProductEntity> getProducts(Query query){
        return mongoTemplate.find(query,ProductEntity.class);
    }

    public List<ProductEntity> getListProductActive(){
        Query query = Query.query(Criteria.where("state").is(true));
        return  mongoTemplate.find(query, ProductEntity.class);
    }

}
