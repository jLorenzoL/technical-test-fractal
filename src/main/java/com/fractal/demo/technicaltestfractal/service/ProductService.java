package com.fractal.demo.technicaltestfractal.service;

import com.fractal.demo.technicaltestfractal.dto.ProductDto;
import com.fractal.demo.technicaltestfractal.dto.request.ProductRequest;
import com.fractal.demo.technicaltestfractal.entity.ProductEntity;
import com.fractal.demo.technicaltestfractal.exception.BussinessExcepcion;
import com.fractal.demo.technicaltestfractal.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<ProductDto> getProductList(ProductRequest productRequest){
        List<ProductDto> lstProducts = new ArrayList<>();
        try {
            List<ProductEntity> lstProductsDB = productRepository.getListProduct(productRequest);
            lstProductsDB.forEach(p->{
                ProductDto productDto = new ProductDto();
                productDto.setId(p.getId());
                productDto.setName(p.getName());
                productDto.setCategory(p.getCategory());
                productDto.setUnitPrice(p.getUnitPrice());
                productDto.setState(p.isState());
                lstProducts.add(productDto);
            });
        } catch (Exception ex) {
            throw new BussinessExcepcion("Something happened during query.");
        }

        return lstProducts;

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
