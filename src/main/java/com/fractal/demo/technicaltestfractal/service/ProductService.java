package com.fractal.demo.technicaltestfractal.service;

import com.fractal.demo.technicaltestfractal.dto.ProductDto;
import com.fractal.demo.technicaltestfractal.dto.request.ProductRequest;
import com.fractal.demo.technicaltestfractal.entity.ProductEntity;
import com.fractal.demo.technicaltestfractal.exception.BussinessExcepcion;
import com.fractal.demo.technicaltestfractal.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public void saveProduct(ProductDto productDto){

        ProductEntity productEntity = new ProductEntity();
        try{
            productEntity.setName(productDto.getName());
            productEntity.setState(true);
            productEntity.setCategory(productDto.getCategory());
            productEntity.setUnitPrice(productDto.getUnitPrice());

            productRepository.saveProduct(productEntity);
        }catch (Exception ex) {
            throw new BussinessExcepcion("An error occures during product resgitration", ex);
        }
    }

    public Map<String,Object> getProductList(ProductRequest productRequest){
        Map<String,Object> resultMap = new HashMap<>();
        List<ProductEntity> lstProducts = new ArrayList<>();
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "name");
            Pageable pageable = PageRequest.of(productRequest.getPage(), productRequest.getMaxResults(), sort);
            Query query = new Query().with(pageable);
            List<ProductEntity> productList = new ArrayList<>();
            if(productRequest.isPaginate()){
                Page<ProductEntity>  productPage = productRepository.getProductsByPagination(query, pageable);
                Long elements = 0L;
                if(Objects.nonNull(productPage)){
                    productList = productPage.getContent();
                    elements = productPage.getTotalElements();
                    resultMap.put("totalPages", productPage.getTotalPages());
                }
                resultMap.put("totalElements", elements);
            }else {
                productList = productRepository.getProducts(query);
            }
            resultMap.put("products", productList);


        } catch (Exception ex) {
            throw new BussinessExcepcion("Something happened during query.");
        }

        return resultMap;

    }

    public List<ProductEntity> getActiveProducts(){

        try {
            return productRepository.getListProductActive();
        }catch (Exception ex) {
            throw new BussinessExcepcion("Something happened during query.");
        }

    }

    public void updateProductById(ProductDto productDto){

        try {
            if(Objects.isNull(productDto.getId())){
                throw new BussinessExcepcion("Theres an error during the process, parameter null");
            }else {
                productRepository.updateInfoProduct(productDto);
            }
        }catch (Exception ex) {
            throw new BussinessExcepcion(ex.getMessage());
        }

    }

    public void deleteProduct(String id){
        try{
            productRepository.deleteProduct(id);
        }catch (Exception ex){
            throw new BussinessExcepcion(ex.getMessage());
        }
    }

    public ProductDto getInfoProduct(String id){

        ProductDto productDto = new ProductDto();
        try {
            if(!Objects.isNull(id)){
                ProductEntity productEntity = productRepository.getProductInfo(id);
                productDto.setId(productEntity.getId());
                productDto.setName(productEntity.getName());
                productDto.setCategory(productEntity.getCategory());
                productDto.setUnitPrice(productEntity.getUnitPrice());
                productDto.setState(productEntity.isState());
            }else {
                throw new BussinessExcepcion("Theres an error during the process, parameter null");
            }
        } catch (Exception ex) {
            throw new BussinessExcepcion(ex.getMessage());
        }

        return productDto;
    }


}
