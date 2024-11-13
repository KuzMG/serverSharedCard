package com.project.sharedCardServer.model.group_users;

import com.project.sharedCardServer.model.group.Group;
import com.project.sharedCardServer.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class GroupUsersId implements Serializable {
    private UUID idUser;
    private UUID idGroup;

}
