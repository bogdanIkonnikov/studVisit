package krefature.studvisit.DAO.mapper;

import krefature.studvisit.infrastructure.entity.Lesson;
import krefature.studvisit.infrastructure.repository.DisciplineRepository;
import krefature.studvisit.infrastructure.repository.GroupRepository;
import krefature.studvisit.infrastructure.repository.TeacherRepository;
import krefature.studvisit.domain.model.LessonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LessonModelMapper {
    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private GroupRepository groupRepo;
    @Autowired
    private DisciplineRepository disciplineRepo;

    public LessonModel toModel(Lesson l){
        if (l == null) return null;
        LessonModel lm = new LessonModel();
        lm.setId(l.getId());
        lm.setUpdated_at(l.getUpdated_at());
        lm.setCreated_at(l.getCreated_at());
        lm.setTime(l.getTime());
        if (l.getGroup() != null) {
            lm.setGroupId(l.getGroup().getId());
        }
        if (l.getTeacher() != null) {
            lm.setTeacherId(l.getTeacher().getId());
        }
        lm.setDate(l.getDate());
        if (l.getDiscipline() != null) {
            lm.setDisciplineId(l.getDiscipline().getId());
        }
        return lm;
    }
    public Lesson toEntity(LessonModel lm){
        if (lm == null) return null;
        Lesson l = new Lesson();
        l.setId(lm.getId());
        l.setTime(lm.getTime());
        l.setUpdated_at(lm.getUpdated_at());
        l.setCreated_at(lm.getCreated_at());
        l.setDate(lm.getDate());
        l.setDiscipline(lm.getDisciplineId() == null ? null : disciplineRepo.findById(lm.getDisciplineId()).orElse(null));
        l.setTeacher(lm.getTeacherId() == null ? null : teacherRepo.findById(lm.getTeacherId()).orElse(null));
        l.setGroup(lm.getGroupId() == null ? null : groupRepo.findById(lm.getGroupId()).orElse(null));
        return l;
    }
}
