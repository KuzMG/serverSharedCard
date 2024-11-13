package com.project.sharedCardServer.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sharedCardServer.model.group_users.GroupUsers;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "group_")
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String name = "";
    private String pic;
    @NotNull
    @DateTimeFormat
    @JsonIgnore
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();


    public Group(String name) {
        this.name = name;
    }

    public Group(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

}
