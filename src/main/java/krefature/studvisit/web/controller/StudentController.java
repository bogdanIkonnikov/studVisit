package krefature.studvisit.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.service.model.StudentModel;
import krefature.studvisit.web.dto.student.CreateStudentRequest;
import krefature.studvisit.web.dto.student.StudentResponse;
import krefature.studvisit.web.dto.student.EditStudentRequest;
import krefature.studvisit.service.service.StudentService;
import krefature.studvisit.web.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper mapper;

    @GetMapping("/getStudentById/{id}")
    public StudentResponse getStudentById(@NotNull(message = "id должен быть указан") @Min(value = 1, message = "id должен быть больше 0") @PathVariable Long id) {
        return mapper.toResponse(studentService.getStudentById(id));
    }

    @GetMapping("/getStudentByGroupId/{id}")
    public List<StudentResponse> getStudentsByGroupId(@NotNull(message = "id должен быть указан") @Min(value = 1, message = "id должен быть больше 0") @PathVariable Long id) {
        return mapper.toResponses(studentService.getStudentsByGroupId(id));
    }

    @PostMapping("/addStudent")
    public StudentResponse addStudent(@Valid @RequestBody CreateStudentRequest request) {
        return mapper.toResponse(studentService.addStudent(mapper.toModel(request)));
    }

    @PutMapping("/editStudent")
    public StudentResponse editStudent(@Valid @RequestBody EditStudentRequest request) {
        StudentModel model = mapper.toModel(request);
        return mapper.toResponse(studentService.updateStudent(model));
    }

    @DeleteMapping("/{id}/delete")
    public void deleteStudent(@NotNull(message = "id должен быть указан") @Min(value = 1, message = "id должен быть больше 0") @PathVariable Long id) {
        studentService.deleteStudent(id);
    }


}
