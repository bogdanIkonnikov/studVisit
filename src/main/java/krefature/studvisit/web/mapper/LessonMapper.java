package krefature.studvisit.web.mapper;

import krefature.studvisit.service.model.LessonModel;
import krefature.studvisit.service.model.LessonModelDates;
import krefature.studvisit.web.dto.lesson.LessonAddRequest;
import krefature.studvisit.web.dto.lesson.LessonResponse;
import krefature.studvisit.web.dto.lesson.LessonWithIdAndDateRequest;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {
    public LessonModelDates toModel(LessonWithIdAndDateRequest request) {
        LessonModelDates model = new LessonModelDates();
        model.setId(request.getId());
        model.setDateAfter(request.getDateAfter());
        model.setDateBefore(request.getDateBefore());
        return model;
    }
    public LessonResponse toResponse(LessonModel model) {
        LessonResponse response = new LessonResponse();
        response.setDate(model.getDate());
        response.setTime(model.getTime());
        response.setTeacherId(model.getTeacherId());
        return response;
    }
    public LessonModel toModel(LessonAddRequest request) {
        LessonModel model = new LessonModel();
        model.setDate(request.getDate());
        model.setGroupId(request.getGroupId());
        model.setTeacherId(request.getTeacherId());
        model.setTime(request.getTime());
        return model;
    }
    public LessonModel toModel(LessonAddRequest request, Long id) {
        LessonModel model = new LessonModel();
        model.setDate(request.getDate());
        model.setGroupId(request.getGroupId());
        model.setTeacherId(request.getTeacherId());
        model.setTime(request.getTime());
        model.setId(id);
        return model;
    }
}
