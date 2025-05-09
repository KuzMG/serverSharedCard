package com.project.sharedCardServer.model.group;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@Data
@Entity(name = "group_token")
@NoArgsConstructor
public class GroupToken {
    @Id
    private UUID id;
    @JoinColumn(name = "id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Group group;
    @NotNull
    @Column(unique = true)
    private String token;
    @NotNull
    private String pic;
    @NotNull
    private Date date;
    @NotNull
    private Integer count;

    public GroupToken(UUID id) {
        this.id = id;
    }
}
