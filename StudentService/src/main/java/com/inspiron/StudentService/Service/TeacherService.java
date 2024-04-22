package com.inspiron.StudentService.Service;

import com.inspiron.StudentService.Dto.TeacherDto;
import com.inspiron.StudentService.Model.Teacher;
import com.inspiron.StudentService.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public class TeacherService {
    
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    public Teacher saveTeacher(TeacherDto teacherDto){
        return teacherRepository.save(processData(teacherDto));
    }
    public Teacher processData(TeacherDto teacherDto){
        Teacher teacher=new Teacher();
        teacher.setTeacherId(teacherDto.getTeacherId());
        teacher.setName(teacherDto.getName());
        teacher.setUserName(teacherDto.getUserName());
        teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
        teacher.setRoles(teacherDto.getRoles());
        return teacher;
    }
}
