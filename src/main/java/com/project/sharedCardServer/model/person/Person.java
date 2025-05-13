package com.project.sharedCardServer.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private Boolean gender;

    @NotNull
    @DateTimeFormat
    @JsonIgnore
    private Date birthday;
    private String pic;

    @JsonProperty("birthday")
    public Long birthday() {
        Long bd = birthday.getTime();
        return bd;
    }
    @JsonProperty("birthday")
    public void birthday(long _date){
        birthday = new Date(_date);
    }

    public Person() {
    }

    public Person(String name, Boolean gender, Date birthday) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public Person(UUID id) {
        this.id = id;
    }

}
