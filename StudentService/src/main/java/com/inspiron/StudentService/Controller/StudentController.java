package com.inspiron.StudentService.Controller;

import com.inspiron.StudentService.Dto.RefreshTokenRequest;
import com.inspiron.StudentService.Dto.StudentDto;
import com.inspiron.StudentService.Model.AuthRequest;
import com.inspiron.StudentService.Model.JwtResponse;
import com.inspiron.StudentService.Model.Student;
import com.inspiron.StudentService.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/home")
    public String get() {
        return "welcome";
    }



    @PostMapping("/save")
    @PostAuthorize("hasAuthority('ROLE_ADMIN')")
    public Student saveStudent(@RequestBody StudentDto studentDto) {
        return studentService.saveStudent(studentDto);
    }

    @GetMapping("/getById")
    @PostAuthorize("hasAuthority('ROLE_TEACHER') or hasAuthority('ROLE_STUDENT')")
    public Student getStudentById(@RequestParam int id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/getName")
    public Student getStudentByName(@RequestParam String userName) {
        return studentService.findByUsername(userName);
    }


    @PostMapping("/authenticate")
    public JwtResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        return studentService.authenticate(authRequest);

    }

    @PostMapping("/register")
    public Student saveStudentDetails(@RequestBody StudentDto studentDto) {


        return studentService.saveStudent(studentDto);
    }

    @GetMapping("/getAll")
    @PostAuthorize("hasAuthority('ROLE_TEACHER')")
    public List<Student> getAllStudentByRole() {

        return studentService.findByRole("ROLE_STUDENT");

    }

    @GetMapping("/g")
    public Page<Student> getAllDataAsPagination(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy){
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
       return  studentService.getAllDataAsPagenation(pageRequest);
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return studentService.refreshToken(refreshTokenRequest);
    }
    @GetMapping("/getAllStudent")
    public List<Student>getAllStudents(){
       return studentService.getAllStudents();
    }


}
