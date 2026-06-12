package krefature.studvisit.web.mapper;

import krefature.studvisit.domain.model.LessonVisitModel;
import krefature.studvisit.infrastructure.repository.StudentRepository;
import krefature.studvisit.web.dto.lessonVisit.LessonVisitRequest;
import krefature.studvisit.web.dto.lessonVisit.LessonVisitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LessonVisitMapper {
    @Autowired
    private StudentRepository studentRepository;

    public LessonVisitModel toModel(LessonVisitRequest request) {
        LessonVisitModel model = new LessonVisitModel();
        model.setStudentIds(request.getStudentIds());
        model.setLessonId(request.getLessonId());
        return model;
    }

    public LessonVisitResponse toResponse(LessonVisitModel model) {
        LessonVisitResponse response = new LessonVisitResponse();
        if (model.getLessonId() != null) {
            response.setName("Занятие " + model.getLessonId());
        }
        if (model.getStudentIds() != null) {
            response.setStudentsFIO(model.getStudentIds().stream()
                    .map(id -> studentRepository.findById(id).orElse(null))
                    .filter(s -> s != null)
                    .map(s -> s.getFirstName() + " " + s.getMiddleName() + " " + s.getLastName())
                    .collect(Collectors.toList()));
        }
        return response;
    }
}


