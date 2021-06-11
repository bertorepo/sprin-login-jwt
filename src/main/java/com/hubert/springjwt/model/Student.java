package com.hubert.springjwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="STUDENT_TBL")
public class Student {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "std_user_name")
  private String userName;

  @Column(name = "std_first_name")
  private String firstName;

  @Column(name = "std_last_name")
  private String lastName;

  @JsonIgnore
  @Column(name = "std_password")
  private String password;

  public String getFullName(){
    return firstName + " " + lastName;
  }
}
