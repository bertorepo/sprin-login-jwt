package com.hubert.springjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDTO {

  private String userName;
  private String firstName;
  private String lastName;
  private String password;
}
