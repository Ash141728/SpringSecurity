package com.inspiron.StudentService.Controller;

import com.inspiron.StudentService.Dto.TeacherDto;
import com.inspiron.StudentService.Model.Teacher;
import com.inspiron.StudentService.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/save")
    public Teacher saveTeacher(@RequestBody TeacherDto teacherDto){

        return teacherService.saveTeacher(teacherDto);
    }
}
