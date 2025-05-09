package com.project.sharedCardServer.restController.dto;

import com.project.sharedCardServer.model.person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonResponse {
    private Person person;
}
