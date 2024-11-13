package com.project.sharedCardServer.model.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Currency {
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
    private String symbol;

}
