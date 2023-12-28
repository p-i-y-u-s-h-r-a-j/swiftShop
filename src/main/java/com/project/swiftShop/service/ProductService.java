package com.project.swiftShop.service;

import com.project.swiftShop.dto.ProductDto;
import com.project.swiftShop.exceptions.CustomException;
import com.project.swiftShop.exceptions.ProductNotExistException;
import com.project.swiftShop.model.Category;
import com.project.swiftShop.model.Product;
import com.project.swiftShop.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription((productDto.getDescription()));
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setImageUrl(productDto.getImageUrl());

        productRepo.save(product);
    }

    public ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setName(product.getName());
        productDto.setDescription((product.getDescription()));
        productDto.setPrice(product.getPrice());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setId(product.getId());

        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepo.findAll();
        List<ProductDto> allProductDto = new ArrayList<>();

        for(Product p: allProducts){
            allProductDto.add(getProductDto(p));
        }

        return allProductDto;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new Exception("Product Not Found!!");
        }
        Product product = optionalProduct.get();

        product.setName(productDto.getName());
        product.setDescription((productDto.getDescription()));
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());

        productRepo.save(product);
    }

    public Product findById(Integer productId) {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistException("Product id is Invalid: "+ productId);
        }
        return optionalProduct.get();
    }
}
