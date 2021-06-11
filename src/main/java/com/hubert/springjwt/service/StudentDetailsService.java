package com.hubert.springjwt.service;

import java.util.ArrayList;

import com.hubert.springjwt.model.Student;
import com.hubert.springjwt.respository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsService implements UserDetailsService {

  @Autowired
  private StudentRepository studentRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    Student student = studentRepository.findByUserName(username);

    return new User(student.getUserName(), student.getPassword(), new ArrayList<>());
  }
  
}
