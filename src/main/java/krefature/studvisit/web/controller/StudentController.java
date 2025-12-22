package krefature.studvisit.web.controller;

import krefature.studvisit.service.model.StudentModel;
import krefature.studvisit.web.dto.student.CreateStudentRequest;
import krefature.studvisit.web.dto.student.StudentResponse;
import krefature.studvisit.web.dto.student.EditStudentRequest;
import krefature.studvisit.service.service.StudentService;
import krefature.studvisit.web.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper mapper;

    @GetMapping("/getStudentById/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return mapper.toResponse(studentService.getStudentById(id));
    }

    @GetMapping("/getStudentByGroupId/{id}")
    public List<StudentResponse> getStudentsByGroupId(@PathVariable Long id) {
        return mapper.toResponses(studentService.getStudentsByGroupId(id));
    }

    @PostMapping("/addStudent")
    public StudentResponse addStudent(@RequestBody CreateStudentRequest request) {
        return mapper.toResponse(studentService.addStudent(mapper.toModel(request)));
    }

    @PutMapping("/editStudent")
    public StudentResponse editStudent(@RequestBody EditStudentRequest request) {
        StudentModel model = mapper.toModel(request);
        return mapper.toResponse(studentService.updateStudent(model));
    }

    @DeleteMapping("/deleteStudent")
    public void deleteStudent(@RequestBody Long id) {
        studentService.deleteStudent(id);
    }


}
