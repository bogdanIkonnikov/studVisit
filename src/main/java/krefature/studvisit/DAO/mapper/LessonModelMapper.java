package krefature.studvisit.DAO.mapper;

import krefature.studvisit.repository.entity.Lesson;
import krefature.studvisit.repository.repository.GroupRepository;
import krefature.studvisit.repository.repository.TeacherRepository;
import krefature.studvisit.service.model.LessonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessonModelMapper {
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private GroupRepository groupRepo;

    public LessonModel toModel(Lesson l){
        LessonModel lm = new LessonModel();
        lm.setId(l.getId());
        lm.setUpdated_at(l.getUpdated_at());
        lm.setCreated_at(l.getCreated_at());
        lm.setTime(l.getTime());
        lm.setGroupId(l.getGroup().getId());
        lm.setTeacherId(l.getTeacher().getId());
        lm.setDate(l.getDate());
        return lm;
    }
    public Lesson toEntity(LessonModel lm){
        Lesson l = new Lesson();
        l.setId(lm.getId());
        l.setTime(lm.getTime());
        l.setUpdated_at(lm.getUpdated_at());
        l.setCreated_at(lm.getCreated_at());
        l.setDate(lm.getDate());
        l.setTeacher(teacherRepo.findById(lm.getTeacherId()).get());
        l.setGroup(groupRepo.findById(lm.getGroupId()).get());
        return l;
    }
}
