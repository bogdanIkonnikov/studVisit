package krefature.studvisit.mapper;

import krefature.studvisit.dto.teacher.TeacherRequest;
import krefature.studvisit.dto.teacher.TeacherResponse;
import krefature.studvisit.entity.Student;
import krefature.studvisit.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public Teacher toEntity(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher();
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
    public TeacherResponse toResponse(Teacher teacher) {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setTeacherFIO(teacher.getFirstName() + " " + teacher.getMiddleName() + " " + teacher.getLastName());
        return teacherResponse;
    }
}
