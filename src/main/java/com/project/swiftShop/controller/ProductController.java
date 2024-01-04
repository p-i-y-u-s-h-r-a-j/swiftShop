package com.project.swiftShop.controller;


import com.project.swiftShop.dto.ProductDto;
import com.project.swiftShop.commonResponse.ApiResponse;
import com.project.swiftShop.model.Category;
import com.project.swiftShop.repository.CategoryRepo;
import com.project.swiftShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if(optionalCategory.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false, "Category Does not Exist"), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "Product Created Successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts(){
        List<ProductDto> allProductDto =  productService.getAllProducts();
        return new ResponseEntity<>(allProductDto, HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalProduct = categoryRepo.findById(productDto.getCategoryId());
        if(optionalProduct.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false, "Category Does Not Exsit"), HttpStatus.BAD_REQUEST);
        }

        productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(new ApiResponse(true, "Product Updated Successfully"), HttpStatus.OK);
    }


}
