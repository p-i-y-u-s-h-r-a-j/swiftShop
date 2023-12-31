package com.project.swiftShop.controller;

import com.project.swiftShop.commonResponse.ApiResponse;
import com.project.swiftShop.model.Category;
import com.project.swiftShop.service.CategoryService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryRestController {


    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.CreateCategory(category);
        return new ResponseEntity<>(new ApiResponse(true,"A New Category Created"), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public List<Category> allCategory(){
        List<Category> allCategory = categoryService.listCategory();
        return allCategory;
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId,@RequestBody Category category){
        Category category1 = categoryService.findById(categoryId);
        if(category1 == null){
            return new ResponseEntity<>(new ApiResponse(false,"Category Does Not Exist"), HttpStatus.OK);
        }
        Category updatedCategory = categoryService.editCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(true,"Category Updated Successfully"), HttpStatus.OK);
    }
}
