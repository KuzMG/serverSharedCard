package com.project.sharedCardServer.restController.dto;

import com.project.sharedCardServer.model.category.Category;
import com.project.sharedCardServer.model.currency.Currency;
import com.project.sharedCardServer.model.metrics.Metric;
import com.project.sharedCardServer.model.product.Product;
import com.project.sharedCardServer.model.recipe.Recipe;
import com.project.sharedCardServer.model.recipe_product.RecipeProduct;
import com.project.sharedCardServer.model.shop.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryResponse {
    private List<Category> categories;
    private List<Currency> currencies;
    private List<Metric> metrics;
    private List<Product> products;
    private List<Recipe> recipes;
    private List<RecipeProduct> recipeProducts;
    private List<Shop> shops;
}
