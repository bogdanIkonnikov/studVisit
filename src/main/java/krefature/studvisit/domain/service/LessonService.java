package krefature.studvisit.domain.service;

import krefature.studvisit.domain.repository.GroupModelRepository;
import krefature.studvisit.domain.repository.DisciplineModelRepository;
import krefature.studvisit.domain.repository.LessonModelRepository;
import krefature.studvisit.domain.repository.TeacherModelRepository;
import krefature.studvisit.common.exceptions.InvalidForeignKeyException;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.domain.model.LessonModel;
import krefature.studvisit.domain.model.LessonModelDates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonModelRepository repository;
    @Autowired
    private TeacherModelRepository teacherModelRepository;
    @Autowired
    private GroupModelRepository groupModelRepository;
    @Autowired
    private DisciplineModelRepository disciplineModelRepository;

    public List<LessonModel> getAllByTeacherIdAndDate(LessonModelDates model) {
        if (!teacherModelRepository.existsById(model.getId())) {
            throw new NotFoundException("Учитель", model.getId());
        }
        return repository.getAllByTeacherIdAndDateBetween(
                model.getId(), model.getDateAfter(), model.getDateBefore()
        );
    }
    public List<LessonModel> getAllByGroupIdAndDate(LessonModelDates model) {
        if (!groupModelRepository.existsById(model.getId())) {
            throw new NotFoundException("Группа", model.getId());
        }
        return repository.getAllByGroupIdAndDateBetween(
                model.getId(), model.getDateAfter(), model.getDateBefore()
        );
    }
    public Page<LessonModel> getPageByTeacherIdAndDate(LessonModelDates model, Pageable pageable) {
        if (!teacherModelRepository.existsById(model.getId())) {
            throw new NotFoundException("Учитель", model.getId());
        }
        return repository.getPageByTeacherIdAndDateBetween(
                model.getId(), model.getDateAfter(), model.getDateBefore(), pageable
        );
    }
    public Page<LessonModel> getPageByGroupIdAndDate(LessonModelDates model, Pageable pageable) {
        if (!groupModelRepository.existsById(model.getId())) {
            throw new NotFoundException("Группа", model.getId());
        }
        return repository.getPageByGroupIdAndDateBetween(
                model.getId(), model.getDateAfter(), model.getDateBefore(), pageable
        );
    }
    public void deleteAllByTeacherId(Long teacherId) {
        if (!teacherModelRepository.existsById(teacherId)) {
            throw new NotFoundException("Учитель", teacherId);
        }
        repository.deleteAllByTeacherId(teacherId);
    }
    public void deleteAllByGroupId(Long groupId) {
        if (!groupModelRepository.existsById(groupId)) {
            throw new NotFoundException("Группа", groupId);
        }
        repository.deleteAllByGroupId(groupId);
    }
    public LessonModel create(LessonModel model) {
        validateForeignKeys(model);
        return repository.save(model);
    }
    public LessonModel update(LessonModel uModel) {
        if (!repository.existsById(uModel.getId())) throw new NotFoundException("Пара", uModel.getId());
        validateForeignKeys(uModel);
        LessonModel model = repository.findById(uModel.getId());
        model.setTeacherId(uModel.getTeacherId());
        model.setDate(uModel.getDate());
        model.setGroupId(uModel.getGroupId());
        model.setDisciplineId(uModel.getDisciplineId());
        model.setTime(uModel.getTime());
        return repository.save(model);
    }
    public LessonModel findById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Пара", id);
        return repository.findById(id);
    }
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Пара", id);
        repository.deleteById(id);
    }

    private void validateForeignKeys(LessonModel model) {
        if (!teacherModelRepository.existsById(model.getTeacherId())) {
            throw new InvalidForeignKeyException(
                    "Учитель с ID " + model.getTeacherId() + " не существует.",
                    "Lesson", "teacherId", model.getTeacherId()
            );
        }
        if (!groupModelRepository.existsById(model.getGroupId())) {
            throw new InvalidForeignKeyException(
                    "Группа с ID " + model.getGroupId() + " не существует.",
                    "Lesson", "groupId", model.getGroupId()
            );
        }
        if (!disciplineModelRepository.existsById(model.getDisciplineId())) {
            throw new InvalidForeignKeyException(
                    "Дисциплина с ID " + model.getDisciplineId() + " не существует.",
                    "Lesson", "disciplineId", model.getDisciplineId()
            );
        }
    }
}
