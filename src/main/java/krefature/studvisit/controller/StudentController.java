package krefature.studvisit.controller;

import krefature.studvisit.dto.student.CreateStudentRequest;
import krefature.studvisit.dto.student.StudentResponse;
import krefature.studvisit.dto.student.EditStudentRequest;
import krefature.studvisit.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudentById/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/getStudentByGroup/{id}")
    public List<StudentResponse> getStudentsByGroup(@PathVariable Long id) {
        return studentService.getStudentsByGroup(id);
    }

    @PostMapping("/addStudent")
    public StudentResponse addStudent(@RequestBody CreateStudentRequest request) {
        return studentService.addStudent(request);
    }

    @PutMapping("/editStudent")
    public StudentResponse editStudent(@RequestBody EditStudentRequest request) {
        return studentService.updateStudent(request);
    }

    @DeleteMapping("/deleteStudent")
    public void deleteStudent(@RequestBody Long id) {
        studentService.deleteStudent(id);
    }


}
