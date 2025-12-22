package krefature.studvisit.web.controller;

import krefature.studvisit.service.model.TeacherModel;
import krefature.studvisit.web.dto.teacher.TeacherRequest;
import krefature.studvisit.web.dto.teacher.TeacherResponse;
import krefature.studvisit.service.service.TeacherService;
import krefature.studvisit.web.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper mapper;

    @GetMapping("/getTeachers")
    public List<TeacherResponse> getAllTeachers() {
        return mapper.toResponses(teacherService.getAllTeachers());
    }

    @GetMapping("/getTeacherById/{id}")
    public TeacherResponse getTeacherById(@PathVariable("id") Long teacherId) {
        return mapper.toResponse(teacherService.getTeacherById(teacherId));
    }

    @PostMapping("/addTeacher")
    public TeacherResponse addTeacher(@RequestBody TeacherRequest teacherRequest) {
        TeacherModel model = mapper.toModel(teacherRequest);
        return mapper.toResponse(teacherService.addTeacher(model));
    }

    @PutMapping("/editTeacher/{id}")
    public TeacherResponse editTeacher(@RequestBody TeacherRequest teacherRequest, @PathVariable("id") Long id) {
        TeacherModel model = mapper.toModel(teacherRequest, id);
        return mapper.toResponse(teacherService.editTeacher(model));
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public void deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
    }
}
