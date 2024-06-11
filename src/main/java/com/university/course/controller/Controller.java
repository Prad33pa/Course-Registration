package com.university.course.controller;

import com.university.course.model.Student;
import com.university.course.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final StudentService studentService;

    @GetMapping(value = "/students/{courseName}")
    public List<Student> getStudents(@PathVariable("courseName") String courseName) {
        return studentService.getStudentsByCourse(courseName);
    }

    @PostMapping(value = "/student")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return new ResponseEntity<>("Student is registered", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/student/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") String studentId) {
        studentService.deleteStudentById(studentId);
        return new ResponseEntity<>("Student is deleted", HttpStatus.OK);
    }
}
