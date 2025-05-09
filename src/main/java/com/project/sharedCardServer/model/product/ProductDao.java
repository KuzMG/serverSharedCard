package com.project.sharedCardServer.model.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDao {
    @Autowired
    private ProductRepository repository;

    public List<Product> getAll() {
        return (List<Product>) repository.findAll();
    }
}
