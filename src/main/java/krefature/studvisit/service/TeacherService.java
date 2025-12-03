package krefature.studvisit.service;

import krefature.studvisit.dto.teacher.TeacherRequest;
import krefature.studvisit.dto.teacher.TeacherResponse;
import krefature.studvisit.entity.Teacher;
import krefature.studvisit.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepo;

    public List<TeacherResponse> getAllTeachers() {
        List<Teacher> teachers = teacherRepo.findAll();
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherResponse teacherResponse = new TeacherResponse();
            teacherResponse.setTeacherFIO(teacher.getFirstName() + " " + teacher.getMiddleName() + " " + teacher.getLastName());
            teacherResponses.add(teacherResponse);
        }
        return teacherResponses;
    }

    public TeacherResponse getTeacherById(Long id) {
        Teacher t = teacherRepo.findById(id).get();
        return new TeacherResponse(t.getFirstName() + " " + t.getMiddleName() + " " + t.getLastName());
    }

    public TeacherResponse addTeacher(TeacherRequest teacherRequest) {
        Teacher t = new Teacher();
        t.setFirstName(teacherRequest.getFirstName());
        t.setMiddleName(teacherRequest.getMiddleName());
        t.setLastName(teacherRequest.getLastName());
        teacherRepo.save(t);
        return new TeacherResponse(t.getFirstName() + " " + t.getMiddleName() + " " + t.getLastName());
    }

    public TeacherResponse editTeacher(Long Id, TeacherRequest teacherRequest) {
        Teacher t = teacherRepo.findById(Id).get();
        t.setFirstName(teacherRequest.getFirstName());
        t.setMiddleName(teacherRequest.getMiddleName());
        t.setLastName(teacherRequest.getLastName());
        teacherRepo.update(t);
        return new TeacherResponse(t.getFirstName() + " " + t.getMiddleName() + " " + t.getLastName());
    }

    public void deleteTeacher(Long id) {
        Teacher t = teacherRepo.findById(id).get();
        teacherRepo.delete(t);
    }
}
