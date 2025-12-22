package krefature.studvisit.web.mapper;

import krefature.studvisit.service.model.StudentModel;
import krefature.studvisit.web.dto.student.CreateStudentRequest;
import krefature.studvisit.web.dto.student.EditStudentRequest;
import krefature.studvisit.web.dto.student.StudentResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {
    public StudentResponse toResponse(StudentModel model) {
        StudentResponse sr = new StudentResponse();
        sr.setFirstName(model.getFirstName());
        sr.setLastName(model.getLastName());
        sr.setMiddleName(model.getMiddleName());
        sr.setId(model.getId());
        sr.setGroupId(model.getGroupId());
        sr.setStatus(model.getStatus());
        return sr;
    }

    public List<StudentResponse> toResponses(List<StudentModel> studentsByGroupId) {
        return studentsByGroupId.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public StudentModel toModel(CreateStudentRequest request) {
        StudentModel s = new StudentModel();
        s.setFirstName(request.getFirstName());
        s.setLastName(request.getLastName());
        s.setMiddleName(request.getMiddleName());
        s.setGroupId(request.getGroupId());
        s.setStatus(request.getStatus());
        return s;
    }

    public StudentModel toModel(EditStudentRequest request) {
        StudentModel s = new StudentModel();
        s.setId(request.getId());
        s.setFirstName(request.getFirstName());
        s.setLastName(request.getLastName());
        s.setMiddleName(request.getMiddleName());
        s.setGroupId(request.getGroupId());
        s.setStatus(request.getStatus());
        return s;
    }
}
