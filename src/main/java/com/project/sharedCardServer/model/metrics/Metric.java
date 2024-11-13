package com.project.sharedCardServer.model.metrics;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Metric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;

    @NotNull
    @Column(name ="name_en")
    @JsonProperty("name_en")
    private String nameEn;

}
