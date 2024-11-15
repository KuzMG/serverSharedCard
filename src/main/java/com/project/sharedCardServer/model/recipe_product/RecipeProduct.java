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
    @JsonProperty("id_product")
    private int idProduct;
    @Id
    @Column(name = "id_recipe", insertable = false, updatable = false)
    @JsonProperty("id_recipe")
    private int idRecipe;
    @Column(name="id_metric", insertable = false, updatable = false)
    @JsonProperty("id_metric")
    private int idMetric;
    private int count;
}
