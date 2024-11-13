package com.project.sharedCardServer.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_account")
public class UserAccount {
    @Id
    private UUID id;
    @JoinColumn(name = "id",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @NotNull
    @Column(unique = true)
    private String email;
    @Column()
    private Date registerAt;
    @NotNull
    @Column(name = "hashed_password")
    @JsonProperty("hashed_password")
    private String hashedPassword;
    @Column(name = "create_code")
    @JsonIgnore
    private String createCode;
    @Column(name = "date_code")
    private Date dateCode;
    @Column(name = "count_code")
    private int countCode;
}
