package krefature.studvisit.service;

import krefature.studvisit.dto.teacher.TeacherRequest;
import krefature.studvisit.dto.teacher.TeacherResponse;
import krefature.studvisit.entity.Teacher;
import krefature.studvisit.mapper.TeacherMapper;
import krefature.studvisit.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepo;
    @Autowired
    private TeacherMapper teacherMapper;

    public List<TeacherResponse> getAllTeachers() {
        List<Teacher> teachers = teacherRepo.findAll();
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherResponse teacherResponse = teacherMapper.toResponse(teacher);
            teacherResponses.add(teacherResponse);
        }
        return teacherResponses;
    }

    public TeacherResponse getTeacherById(Long id) {
        Teacher t = teacherRepo.findById(id).get();
        return teacherMapper.toResponse(t);
    }

    public TeacherResponse addTeacher(TeacherRequest teacherRequest) {
        Teacher t = teacherMapper.toEntity(teacherRequest);
        Teacher savedTeacher =  teacherRepo.save(t);
        return teacherMapper.toResponse(savedTeacher);
    }

    public TeacherResponse editTeacher(Long Id, TeacherRequest teacherRequest) {
        Teacher t = teacherRepo.findById(Id).orElse(null);
        teacherMapper.updateEntity(t, teacherRequest);
        Teacher updatedTeacher = teacherRepo.save(t);
        return teacherMapper.toResponse(updatedTeacher);
    }

    public void deleteTeacher(Long id) {
        Teacher t = teacherRepo.findById(id).get();
        teacherRepo.delete(t);
    }
}
