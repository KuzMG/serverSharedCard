package com.project.sharedCardServer.model.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;
    @NotNull
    @Column(name = "name_en")
    @JsonProperty("name_en")
    private String nameEn;

    @NotNull
    @Column(name = "type",insertable = false, updatable = false)
    private int type;

    private String pic;

    private String color;

    @JoinColumn(name = "type",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CategoryTypes categoryType;

    public Category(String name, String nameEn,String pic,String color, int type) {
        this.name = name;
        this.nameEn = nameEn;
        this.pic = pic;
        this.color = color;
        this.type = type;
    }

    public Category(String name, String nameEn, CategoryTypes categoryType) {
        this.name = name;
        this.nameEn = nameEn;
        this.categoryType = categoryType;
    }

    public Category() {

    }
}
