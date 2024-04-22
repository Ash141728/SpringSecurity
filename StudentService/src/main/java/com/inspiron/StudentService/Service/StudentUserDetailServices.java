package com.inspiron.StudentService.Service;

import com.inspiron.StudentService.Model.StudentUserDetails;
import com.inspiron.StudentService.Model.Student;
import com.inspiron.StudentService.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentUserDetailServices implements UserDetailsService {

    @Autowired
    StudentRepository studentRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> byUserName = studentRepository.findByUserName(username);
       return  byUserName.map(StudentUserDetails::new).orElseThrow(()->new UsernameNotFoundException("user not found"+username));
    }
}
