package com.project.sharedCardServer.model.person;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "account")
public class PersonAccount {
    @Id
    private UUID id;
    @JoinColumn(name = "id",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;
    @NotNull
    @Column(unique = true)
    private String email;
    @Column(name = "creation_date")
    @JsonProperty("creation_date")
    private Date creationDate;
    @NotNull
    @Column(name = "hashed_password")
    @JsonIgnore
    private String hashedPassword;
    @Column(name = "code")
    @JsonIgnore
    private String code;
    @Column(name = "date_code")
    @JsonIgnore
    private Date dateCode;
    @NotNull
    @Column(name = "count_code")
    @JsonIgnore
    private Integer countCode;
}
