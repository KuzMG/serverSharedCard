package com.project.sharedCardServer.model.recipe_product;

import com.project.sharedCardServer.model.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeProductDao {
    @Autowired
    private RecipeProductRepository repository;


    public List<RecipeProduct> getAll() {
        return (List<RecipeProduct>) repository.findAll();
    }
}
