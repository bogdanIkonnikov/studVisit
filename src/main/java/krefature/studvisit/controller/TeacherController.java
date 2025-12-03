package krefature.studvisit.controller;

import krefature.studvisit.dto.teacher.TeacherRequest;
import krefature.studvisit.dto.teacher.TeacherResponse;
import krefature.studvisit.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeachers")
    public List<TeacherResponse> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/getTeacherById/{id}")
    public TeacherResponse getTeacherById(@PathVariable("id") Long teacherId) {
        return teacherService.getTeacherById(teacherId);
    }

    @PostMapping("/addTeacher")
    public TeacherResponse addTeacher(@RequestBody TeacherRequest teacherRequest) {
        return teacherService.addTeacher(teacherRequest);
    }

    @PutMapping("/editTeacher/{id}")
    public TeacherResponse editTeacher(@RequestBody TeacherRequest teacherRequest, @PathVariable("id") Long id) {
        return teacherService.editTeacher(id, teacherRequest);
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public void deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
    }
}
