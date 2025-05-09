package com.project.sharedCardServer.model.category_product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryProductDao {
    @Autowired
    private CategoryProductRepository categoryProductRepository;
    public List<CategoryProduct> getAll(){
        return (List<CategoryProduct>) categoryProductRepository.findAll();
    }

}
