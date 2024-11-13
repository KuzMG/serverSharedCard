package com.project.sharedCardServer.model.recipe_product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class RecipeProductId implements Serializable {
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "id_recipe")
    private int idRecipe;

    public RecipeProductId() {
    }

    public RecipeProductId(int idProduct, int idRecipe) {
        this.idProduct = idProduct;
        this.idRecipe = idRecipe;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }
}
