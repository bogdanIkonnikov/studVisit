package krefature.studvisit.mapper;

import krefature.studvisit.dto.student.CreateStudentRequest;
import krefature.studvisit.dto.student.EditStudentRequest;
import krefature.studvisit.dto.student.StudentResponse;
import krefature.studvisit.entity.Group;
import krefature.studvisit.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student toEntity(CreateStudentRequest createStudentRequest, Group group) {
        Student s = new Student();
        s.setFirstName(createStudentRequest.getFirstName());
        s.setLastName(createStudentRequest.getLastName());
        s.setMiddleName(createStudentRequest.getMiddleName());
        s.setStatus(createStudentRequest.getStatus());
        s.setGroup(group);
        return s;
    }
    public void updateEntity(EditStudentRequest request, Student student,Group group) {
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setMiddleName(request.getMiddleName());
        student.setStatus(request.getStatus());
        student.setGroup(group);
    }
    public StudentResponse toResponse(Student student) {
        StudentResponse sr = new StudentResponse();
        sr.setFirstName(student.getFirstName());
        sr.setLastName(student.getLastName());
        sr.setMiddleName(student.getMiddleName());
        sr.setId(student.getId());
        sr.setGroupName(student.getGroup().getName());
        sr.setStatus(student.getStatus());
        return sr;
    }
}
