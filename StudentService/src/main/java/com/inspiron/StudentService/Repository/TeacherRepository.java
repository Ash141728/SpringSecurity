package com.inspiron.StudentService.Repository;

import com.inspiron.StudentService.Model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherRepository  extends MongoRepository<Teacher,Integer> {

}
