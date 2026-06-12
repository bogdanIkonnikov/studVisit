package krefature.studvisit.DAO.mapper;

import krefature.studvisit.domain.model.LessonVisitModel;
import krefature.studvisit.infrastructure.entity.LessonVisit;
import krefature.studvisit.infrastructure.repository.LessonRepository;
import krefature.studvisit.infrastructure.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LessonVisitModelMapper {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public LessonVisitModel toModel(LessonVisit entity) {
        if (entity == null) return null;
        LessonVisitModel model = new LessonVisitModel();
        model.setId(entity.getId());
        if (entity.getLesson() != null) {
            model.setLessonId(entity.getLesson().getId());
        }
        if (entity.getStudents() != null) {
            model.setStudentIds(entity.getStudents().stream()
                    .map(s -> s.getId())
                    .collect(Collectors.toList()));
        }
        return model;
    }

    public LessonVisit toEntity(LessonVisitModel model) {
        if (model == null) return null;
        LessonVisit entity = new LessonVisit();
        entity.setId(model.getId());
        entity.setLesson(lessonRepository.findById(model.getLessonId()).orElse(null));
        if (model.getStudentIds() != null) {
            entity.setStudents(model.getStudentIds().stream()
                    .map(id -> studentRepository.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        return entity;
    }
}

