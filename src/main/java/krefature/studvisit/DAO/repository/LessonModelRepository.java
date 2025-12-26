package krefature.studvisit.DAO.repository;

import krefature.studvisit.service.model.LessonModel;

import java.util.List;

public interface LessonModelRepository {

    List<LessonModel> getAllByTeacherIdAndDateBetween(Long teacherId, String dateBefore, String dateAfter);

    List<LessonModel> getAllByGroupIdAndDateBetween(Long groupId, String dateBefore, String dateAfter);

    void deleteAllByTeacherId(Long teacherId);

    void deleteAllByGroupId(Long groupId);

    LessonModel save(LessonModel model);

    LessonModel findById(Long id);

}
