package com.project.sharedCardServer.model.recipe_product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.metrics.Metric;
import com.project.sharedCardServer.model.product.Product;
import com.project.sharedCardServer.model.recipe.Recipe;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(RecipeProductId.class)
public class RecipeProduct {
    @Id
    @Column(name = "id_product", insertable = false, updatable = false)
    private Integer idProduct;
    @Id
    @Column(name = "id_recipe", insertable = false, updatable = false)
    private Integer idRecipe;
    @Column(name="id_metric", insertable = false, updatable = false)
    private Integer idMetric;
    private Integer count;
}
