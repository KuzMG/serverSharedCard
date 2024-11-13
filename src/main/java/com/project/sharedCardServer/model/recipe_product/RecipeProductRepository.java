package com.project.sharedCardServer.model.recipe_product;

import com.project.sharedCardServer.model.recipe.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeProductRepository extends CrudRepository<RecipeProduct, RecipeProductId> {
}
