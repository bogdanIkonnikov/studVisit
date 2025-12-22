package krefature.studvisit.DAO.mapper;

import krefature.studvisit.repository.entity.Teacher;
import krefature.studvisit.service.model.TeacherModel;
import org.springframework.stereotype.Component;

@Component
public class TeacherModelMapper {
    public TeacherModel toModel(Teacher teacher){
        TeacherModel model = new TeacherModel();
        model.setFirstName(teacher.getFirstName());
        model.setLastName(teacher.getLastName());
        model.setId(teacher.getId());
        model.setMiddleName(teacher.getMiddleName());
        model.setCreated_at(teacher.getCreated_at());
        model.setUpdated_at(teacher.getUpdated_at());
        return model;
    }
    public Teacher toEntity(TeacherModel model){
        Teacher teacher = new Teacher();
        teacher.setFirstName(model.getFirstName());
        teacher.setLastName(model.getLastName());
        teacher.setId(model.getId());
        teacher.setMiddleName(model.getMiddleName());
        teacher.setCreated_at(model.getCreated_at());
        teacher.setUpdated_at(model.getUpdated_at());
        return teacher;
    }
}
