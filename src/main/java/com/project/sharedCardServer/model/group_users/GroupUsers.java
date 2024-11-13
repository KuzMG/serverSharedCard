package com.project.sharedCardServer.model.group_users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@Data
@Entity(name = "group_users")
@IdClass(GroupUsersId.class)
@AllArgsConstructor
@NoArgsConstructor
public class GroupUsers {
    public static final int CREATOR = 2;
    public static final int ADMIN = 1;
    public static final int USER = 0;

    @Id
    @JsonProperty("id_user")
    private UUID idUser;
    @Id
    @JsonProperty("id_group")
    private UUID idGroup;
    @NotNull
    private int status;


    @NotNull
    @Column(name = "date_invite")
    @JsonIgnore
    private Date dateInvite = new Date();
}
