package com.inspiron.StudentService.Repository;

import com.inspiron.StudentService.Model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student,Integer> , PagingAndSortingRepository<Student,Integer> {
    Optional<Student> findByUserName(String userName);
    List<Student> findByRoles(String roles);
    @Query("{'someField': {$regex: ?0}}")
    List<Student> findByRegexPattern(String regexPattern);
}
