package krefature.studvisit.web.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.service.model.TeacherModel;
import krefature.studvisit.web.dto.teacher.TeacherRequest;
import krefature.studvisit.web.dto.teacher.TeacherResponse;
import krefature.studvisit.service.service.TeacherService;
import krefature.studvisit.web.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Validated
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper mapper;

    @GetMapping("")
    public List<TeacherResponse> getAllTeachers() {
        return mapper.toResponses(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public TeacherResponse getTeacherById(@NotNull(message = "id должен быть указан") @Min(value = 1, message = "id должен быть больше 0") @PathVariable("id") Long teacherId) {
        return mapper.toResponse(teacherService.getTeacherById(teacherId));
    }

    @PostMapping("/add")
    public TeacherResponse addTeacher(@RequestBody TeacherRequest teacherRequest) {
        TeacherModel model = mapper.toModel(teacherRequest);
        return mapper.toResponse(teacherService.addTeacher(model));
    }

    @PutMapping("/{id}/edit")
    public TeacherResponse editTeacher(@RequestBody TeacherRequest teacherRequest, @PathVariable("id") Long id) {
        TeacherModel model = mapper.toModel(teacherRequest, id);
        return mapper.toResponse(teacherService.editTeacher(model));
    }

    @DeleteMapping("/{id}/delete")
    public void deleteTeacher(@NotNull(message = "id должен быть указан") @Min(value = 1, message = "id должен быть больше 0") @PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
    }
}
