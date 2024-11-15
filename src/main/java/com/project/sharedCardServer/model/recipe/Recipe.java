package com.project.sharedCardServer.model.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    @Column(name = "name_en")
    @JsonProperty("name_en")
    private String nameEn;
    @Column(columnDefinition="TEXT")
    private String description;
    private Integer portion;
    private Integer calories;
    private Double protein;
    private Double fat;
    private Double carb;
    private String pic;
    @NotNull
    @Column(name = "id_category",insertable = false, updatable = false)
    @JsonProperty("id_category")
    private int idCategory;
    @JoinColumn(name = "id_category",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY,targetEntity = Category.class)
    private Category category;
}
