package com.hubert.springjwt.respository;

import com.hubert.springjwt.model.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  
  Student findByUserName(String userName);
}
