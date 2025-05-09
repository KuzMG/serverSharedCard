package com.project.sharedCardServer.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.category_product.CategoryProduct;
import com.project.sharedCardServer.model.metric.Metric;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Column(name = "name_en")
    @JsonProperty("name_en")
    private String nameEn;
    @NotNull
    @Column(name = "id_category_product", insertable = false, updatable = false)
    @JsonProperty("id_category_product")
    private Integer idCategory;
    @JoinColumn(name = "id_category_product", referencedColumnName = "id")
    @OneToOne(targetEntity = CategoryProduct.class, fetch = FetchType.LAZY)
    private CategoryProduct category;
    @NotNull
    @Column(name = "quantity_multiplier", insertable = false, updatable = false)
    @JsonProperty("quantity_multiplier")
    private Integer quantityMultiplier;
    @NotNull
    @Column(name = "id_metric", insertable = false, updatable = false)
    @JsonProperty("id_metric")
    private Integer idMetric;
    @JoinColumn(name = "id_metric", referencedColumnName = "id")
    @OneToOne(targetEntity = Metric.class, fetch = FetchType.LAZY)
    private Metric metric;
    private Double fat;
    private Double protein;
    private Double carb;
    private Integer calories;
}
