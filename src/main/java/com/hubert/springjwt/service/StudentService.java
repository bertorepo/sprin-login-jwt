package com.hubert.springjwt.service;

import javax.transaction.Transactional;

import com.hubert.springjwt.dto.StudentDTO;
import com.hubert.springjwt.model.Student;
import com.hubert.springjwt.respository.StudentRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  
  private final StudentRepository studentRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  @Autowired
  public StudentService(StudentRepository studentRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
    this.studentRepository = studentRepository;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
  }

  @Transactional
  public Student saveStudent(Student std){
    return studentRepository.save(std);
  }

  public Student findStudentByUsername(String username){
    return studentRepository.findByUserName(username);
  }

  public Student registerStudent(StudentDTO std){
    String password = passwordEncoder.encode(std.getPassword());
    std.setPassword(password);
    Student student = new Student();
    modelMapper.map(std, student);
    
    return saveStudent(student);
  }
}
