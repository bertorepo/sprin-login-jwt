package com.hubert.springjwt.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hubert.springjwt.dto.StudentDTO;
import com.hubert.springjwt.model.Student;
import com.hubert.springjwt.service.StudentService;
import com.hubert.springjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  private final StudentService studentService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private String TOKEN = null;

  @Autowired
  public StudentController(StudentService studentService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.studentService = studentService;
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  public Map<String, String> getDetails(HttpServletRequest request, HttpServletResponse response){
    String header = request.getHeader("Authorization");
    if(header != null && header.startsWith("Bearer ")){
      String extractToken = header.substring(7);
      TOKEN = extractToken;
    }

    Map<String, String> authDetails = new HashMap<>();
    Student student = studentService.findStudentByUsername(request.getUserPrincipal().getName());
    //add the cookie for authenticated api endpoint response
    response.addCookie(setCookie(student.getUserName()));
    authDetails.put("username", student.getUserName());
    authDetails.put("fullName", student.getFullName());
    authDetails.put("token ", TOKEN);
    
    return authDetails;
  }

  public Cookie setCookie(String username){   
    String encodedCookieString = username + TOKEN;
    String encodedString = Base64.getEncoder().encodeToString(encodedCookieString.getBytes());
    Cookie jwtCookie = new Cookie("X-Custom-Cookie", encodedString);
    jwtCookie.setMaxAge(86400);
    jwtCookie.setSecure(true);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setPath("/");
   //  jwtCookie.setDomain("localhost:8080");

   return jwtCookie;
  }

  @GetMapping("/")
    public ResponseEntity<?> welcomePage(HttpServletRequest request, HttpServletResponse response){
      Map<String, String> authDetails = getDetails(request, response);
      return new ResponseEntity<>(authDetails, HttpStatus.OK);
    }

   @PostMapping("/register") 
   public Student registerStudent(@RequestBody StudentDTO std){
     return studentService.registerStudent(std);
   }

   @PostMapping("/authenticate")
   public ResponseEntity<String> generateToken(@RequestBody StudentDTO std, HttpServletResponse response) throws Exception{
     try {
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(std.getUserName(), std.getPassword()));
     } catch (Exception e) {
      return new ResponseEntity<>("Invalid Username/Password", HttpStatus.BAD_REQUEST);
     }
     TOKEN = jwtUtil.generateToken(std.getUserName());
     response.addCookie(setCookie(std.getUserName()));
     return new ResponseEntity<>(TOKEN, HttpStatus.OK);
   }
}
