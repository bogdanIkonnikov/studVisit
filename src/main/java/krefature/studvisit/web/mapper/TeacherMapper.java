package krefature.studvisit.web.mapper;

import krefature.studvisit.service.model.TeacherModel;
import krefature.studvisit.web.dto.teacher.TeacherRequest;
import krefature.studvisit.web.dto.teacher.TeacherResponse;
import krefature.studvisit.repository.entity.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherMapper {
    public TeacherModel toModel(TeacherRequest teacherRequest) {
        TeacherModel teacher = new TeacherModel();
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setMiddleName(teacherRequest.getMiddleName());
        return teacher;
    }
    public TeacherModel toModel(TeacherRequest teacherRequest, Long teacherId) {
        TeacherModel teacher = new TeacherModel();
        teacher.setId(teacherId);
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setMiddleName(teacherRequest.getMiddleName());
        return teacher;
    }
    public void updateEntity(Teacher teacher, TeacherRequest teacherRequest) {
        teacher.setFirstName(teacherRequest.getFirstName());
        teacher.setLastName(teacherRequest.getLastName());
        teacher.setMiddleName(teacherRequest.getMiddleName());
    }
    public TeacherResponse toResponse(TeacherModel teacher) {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setTeacherFIO(teacher.getFirstName() + " " + teacher.getMiddleName() + " " + teacher.getLastName());
        return teacherResponse;
    }

    public List<TeacherResponse> toResponses(List<TeacherModel> models) {
        return models.stream().map(this::toResponse).toList();
    }
}
