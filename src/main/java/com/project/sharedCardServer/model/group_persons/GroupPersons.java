package com.project.sharedCardServer.model.group_persons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@Entity(name = "group_persons")
@IdClass(GroupPersonsId.class)
@AllArgsConstructor
@NoArgsConstructor
public class GroupPersons {
    public static final int CREATOR = 2;
    public static final int ADMIN = 1;
    public static final int USER = 0;

    @Id
    @JsonProperty("id_person")
    private UUID idPerson;
    @Id
    @JsonProperty("id_group")
    private UUID idGroup;
    @NotNull
    private Integer status;


    @NotNull
    @Column(name = "date_invite")
    @JsonIgnore
    private Date dateInvite = new Date();
}
