package com.project.sharedCardServer.model.recipe;

import com.project.sharedCardServer.model.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
}
