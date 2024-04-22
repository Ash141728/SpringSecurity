package com.inspiron.StudentService.Service;

import com.inspiron.StudentService.Dto.RefreshTokenRequest;
import com.inspiron.StudentService.Dto.StudentDto;
import com.inspiron.StudentService.Exception.NoStudentFoundException;
import com.inspiron.StudentService.Model.AuthRequest;
import com.inspiron.StudentService.Model.JwtResponse;
import com.inspiron.StudentService.Model.RefreshToken;
import com.inspiron.StudentService.Model.Student;
import com.inspiron.StudentService.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    JwtService jwtService;

    public Page<Student> getAllDataAsPagenation(Pageable pageable){
        return studentRepository.findAll(pageable);
    }

    public Student saveStudent(StudentDto studentDto) {

        Student student = processData(studentDto);
        return studentRepository.save(student);
    }

    public Student getStudentById(int id) {
        Optional<Student> studentById = studentRepository.findById(id);
        return studentById.orElseThrow(()
                -> new NoStudentFoundException("no student found with this id"));


    }

    public Student findByUsername(String userName) {

        Optional<Student> byUsername = studentRepository.findByUserName(userName);
        return byUsername.orElseThrow(()
                -> new NoStudentFoundException("no student found with this id"));
    }

    public Student processData(StudentDto studentDto) {
        Student student = new Student();
        student.setStudentId(studentDto.getStudentId());
        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());
        student.setUserName(studentDto.getUserName());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        student.setRoles(studentDto.getRoles());

        return student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findByRole(String roles) {
        return studentRepository.findByRoles(roles);
    }

    /**
     * This method will authenticate the user
     * and generate respective token for given period of time
     * @return JwtResponse
     */
    public JwtResponse authenticate(AuthRequest authRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());

            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(jwtService.generateToken(authRequest.getUsername()));
            jwtResponse.setToken(refreshToken.getToken());
            return jwtResponse;

        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    /**
     * This Method is responsible for refreshing token after access token get expired
     * @return JwtResponse
     */
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getStudent)
                .map(student -> {
                    String accessToken = jwtService.generateToken(student.getName());
                    JwtResponse jwtResponse = new JwtResponse();
                    jwtResponse.setAccessToken(accessToken);
                    jwtResponse.setToken(refreshTokenRequest.getToken());
                    return jwtResponse;
                }).orElseThrow(() -> new RuntimeException("Refresh Token Is not in Database"));


    }
}
