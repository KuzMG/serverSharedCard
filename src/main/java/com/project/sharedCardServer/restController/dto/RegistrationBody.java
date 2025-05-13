package com.project.sharedCardServer.restController.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationBody {
   private String email;
   private String password;
   private String name;
   private Long date;
   private Boolean gender;
}
