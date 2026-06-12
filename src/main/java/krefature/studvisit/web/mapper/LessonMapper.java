package krefature.studvisit.web.mapper;

import krefature.studvisit.domain.repository.LessonVisitModelRepository;
import krefature.studvisit.domain.model.LessonModel;
import krefature.studvisit.domain.model.LessonModelDates;
import krefature.studvisit.web.dto.lesson.LessonAddRequest;
import krefature.studvisit.web.dto.lesson.LessonResponse;
import krefature.studvisit.web.dto.lesson.LessonWithIdAndDateRequest;
import krefature.studvisit.web.dto.lesson.LessonWithVisitResponse;
import krefature.studvisit.infrastructure.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LessonMapper {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonVisitModelRepository visitRepository;

    public LessonModelDates toModel(LessonWithIdAndDateRequest request) {
        LessonModelDates model = new LessonModelDates();
        model.setId(request.getId());
        model.setDateAfter(request.getDateAfter());
        model.setDateBefore(request.getDateBefore());
        return model;
    }
    public LessonResponse toResponse(LessonModel model) {
        LessonResponse response = new LessonResponse();
        response.setId(model.getId());
        response.setDate(model.getDate());
        response.setTime(model.getTime());
        response.setTeacherId(model.getTeacherId());
        response.setDisciplineId(model.getDisciplineId());
        response.setGroupId(model.getGroupId());
        
        if (model.getTeacherId() != null) {
            var teacher = teacherRepository.findById(model.getTeacherId()).orElse(null);
            if (teacher != null) {
                response.setTeacherFIO(teacher.getFirstName() + " " + teacher.getMiddleName() + " " + teacher.getLastName());
            }
        }
        return response;
    }
    public LessonModel toModel(LessonAddRequest request) {
        LessonModel model = new LessonModel();
        model.setDate(request.getDate());
        model.setGroupId(request.getGroupId());
        model.setTeacherId(request.getTeacherId());
        model.setDisciplineId(request.getDisciplineId());
        model.setTime(request.getTime());
        return model;
    }
    public LessonModel toModel(LessonAddRequest request, Long id) {
        LessonModel model = new LessonModel();
        model.setDate(request.getDate());
        model.setGroupId(request.getGroupId());
        model.setTeacherId(request.getTeacherId());
        model.setDisciplineId(request.getDisciplineId());
        model.setTime(request.getTime());
        model.setId(id);
        return model;
    }

    public LessonWithVisitResponse toVisitResponse(LessonModel model) {
        LessonWithVisitResponse response = new LessonWithVisitResponse();
        response.setId(model.getId());
        response.setDate(model.getDate());
        response.setTime(model.getTime());
        response.setTeacherId(model.getTeacherId());
        response.setGroupId(model.getGroupId());
        response.setDisciplineId(model.getDisciplineId());
        var visit = visitRepository.findByLessonId(model.getId());
        response.setStudentIds(visit != null ? visit.getStudentIds() : new ArrayList<>());

        if (model.getTeacherId() != null) {
            var teacher = teacherRepository.findById(model.getTeacherId()).orElse(null);
            if (teacher != null) {
                response.setTeacherFIO(teacher.getFirstName() + " " + teacher.getMiddleName() + " " + teacher.getLastName());
            }
        }
        return response;

    }
}
