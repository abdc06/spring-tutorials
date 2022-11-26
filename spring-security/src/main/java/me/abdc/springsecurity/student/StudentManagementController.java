package me.abdc.springsecurity.student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

    private static List<Student> students = List.of(
            new Student(1L, "김태호"),
            new Student(2L, "이호진"),
            new Student(3L, "박진우")
    );

    @GetMapping
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Long studentId) {
        return students.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exists"));
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @PutMapping("/{studentId}")
    public void updateStudent(@PathVariable Long studentId, @RequestBody Student student) {
        System.out.println(String.format("%s, %s", studentId, student));
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        System.out.println(studentId);
    }
}
