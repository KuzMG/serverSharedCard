package com.project.sharedCardServer.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.category.Category;
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
    @Column(name = "id_category", insertable = false, updatable = false)
    @JsonProperty("id_category")
    private Integer idCategory;
    @JoinColumn(name = "id_category",referencedColumnName = "id")
    @OneToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    private Category category;
    private Double fat;
    private Double protein;
    private Double carb;
    private Double calorie;
    @NotNull
    private Boolean allergy;
}
