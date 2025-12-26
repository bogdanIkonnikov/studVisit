package krefature.studvisit.service.service;

import krefature.studvisit.DAO.repository.LessonModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.service.model.LessonModel;
import krefature.studvisit.service.model.LessonModelDates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonModelRepository repository;

    public List<LessonModel> getAllByTeacherIdAndDate(LessonModelDates model) {
        return repository.getAllByTeacherIdAndDateBetween(
                model.getId(), model.getDateBefore(), model.getDateAfter()
        );
    }
    public List<LessonModel> getAllByGroupIdAndDate(LessonModelDates model) {
        return repository.getAllByGroupIdAndDateBetween(
                model.getId(), model.getDateBefore(), model.getDateAfter()
        );
    }
    public void deleteAllByTeacherId(Long teacherId) {
        repository.deleteAllByTeacherId(teacherId);
    }
    public void deleteAllByGroupId(Long groupId) {
        repository.deleteAllByGroupId(groupId);
    }
    public LessonModel create(LessonModel model) {
        return repository.save(model);
    }
    public LessonModel update(LessonModel uModel) {
        LessonModel model = repository.findById(uModel.getId());
        if (model == null) throw new NotFoundException("Пара", model.getId());
        model.setTeacherId(uModel.getTeacherId());
        model.setDate(uModel.getDate());
        model.setGroupId(uModel.getGroupId());
        model.setTime(uModel.getTime());
        return repository.save(model);
    }
    public LessonModel findById(Long id) {
        return repository.findById(id);
    }
}
