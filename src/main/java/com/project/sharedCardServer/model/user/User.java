package com.project.sharedCardServer.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private Boolean gender;
    @NotNull
    private Double weight;
    @NotNull
    private Integer height;
    @NotNull
    @DateTimeFormat
    @Column(name = "date")
    @JsonIgnore
    private Date birthday;
    private String pic;

    @JsonProperty("date")
    public Long dateFirst() {
        Long bd = birthday.getTime();
        return bd;
    }
    @JsonProperty("date")
    public void dateFirst(long _date){
        birthday = new Date(_date);
    }

    public User() {
    }

    public User(String name, Boolean gender, Double weight, Integer height, Date birthday) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.birthday = birthday;
    }

    public User(UUID id) {
        this.id = id;
    }

}
