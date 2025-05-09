package com.project.sharedCardServer.model.group_persons;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class GroupPersonsId implements Serializable {
    private UUID idPerson;
    private UUID idGroup;

}
