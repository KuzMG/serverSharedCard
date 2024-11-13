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
    private String description;
    private int portion;
    private double calories;
    @NotNull
    @Column(name = "id_category",insertable = false, updatable = false)
    @JsonProperty("id_category")
    private int idCategory;
    @JoinColumn(name = "id_category",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY,targetEntity = Category.class)
    private Category category;
}
