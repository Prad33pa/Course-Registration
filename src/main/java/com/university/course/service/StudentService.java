package com.university.course.service;

import com.university.course.model.Course;
import com.university.course.model.Student;
import com.university.course.repository.CourseRepository;
import com.university.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Map<Long, Set<Long>> studentCourseMap = new HashMap<>(); // Key: Course ID, Value: Set of Student IDs

    public StudentService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
        // Remove student from all courses
        studentCourseMap.values().forEach(students -> students.remove(studentId));
    }

    public void addStudentToCourse(Long studentId, Long courseId) {
        studentCourseMap.computeIfAbsent(courseId, k -> new HashSet<>()).add(studentId);
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        Course course = courseRepository.findByName(courseName);
        if (course == null) {
            return Collections.emptyList();
        }

        Set<Long> studentIds = studentCourseMap.getOrDefault(course.getId(), Collections.emptySet());
        List<Student> students = studentRepository.findAllById(studentIds);
        students.sort(Comparator.comparing(Student::getName));
        return students;
    }
}
