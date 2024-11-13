package com.project.sharedCardServer.model.category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity(name = "category_types")
public class CategoryTypes {
    @Id
    private int id;
    @NotNull
    private String name;

    public CategoryTypes() {
    }

    public CategoryTypes(int id) {
        this.id = id;
    }
}
