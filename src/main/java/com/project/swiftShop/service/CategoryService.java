package com.project.swiftShop.service;


import com.project.swiftShop.model.Category;
import com.project.swiftShop.repository.CategoryRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    EntityManager entityManager;
    @Autowired
    CategoryRepo categoryRepo;
    public void CreateCategory(Category category){
        categoryRepo.save(category);
    }

    public List<Category> listCategory(){
        List<Category> allCategory = categoryRepo.findAll();
        return allCategory;
    }


    @Transactional
    public Category editCategory(int theId, Category updatedCategory){
        Category category = entityManager.find(Category.class, theId);
        category.setCategoryName(updatedCategory.getCategoryName());
        category.setDescription(updatedCategory.getDescription());
        category.setImageUrl(updatedCategory.getImageUrl());
        categoryRepo.save(category);
        return category;

    }

    public Category findById(int categoryId) {
        Category category = entityManager.find(Category.class, categoryId);
        return category;
    }
}
