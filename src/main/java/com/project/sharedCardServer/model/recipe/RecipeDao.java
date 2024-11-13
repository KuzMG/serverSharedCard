package com.project.sharedCardServer.model.recipe;

import com.project.sharedCardServer.model.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeDao {
    @Autowired
    private RecipeRepository repository;

    public List<Recipe> getAll() {
        return (List<Recipe>) repository.findAll();
    }
}
