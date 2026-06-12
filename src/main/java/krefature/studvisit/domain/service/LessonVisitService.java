package krefature.studvisit.domain.service;

import krefature.studvisit.domain.repository.LessonModelRepository;
import krefature.studvisit.domain.repository.LessonVisitModelRepository;
import krefature.studvisit.domain.repository.StudentModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.domain.model.LessonVisitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonVisitService {
    @Autowired
    private LessonVisitModelRepository repository;
    @Autowired
    private LessonModelRepository lessonModelRepository;
    @Autowired
    private StudentModelRepository studentModelRepository;

    public LessonVisitModel addLessonVisit(LessonVisitModel model) {
        validateReferences(model);
        return repository.save(model);
    }

    public LessonVisitModel getLessonVisitById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Посещение занятия", id);
        return repository.findById(id);
    }

    public List<LessonVisitModel> getAllLessonVisits() {
        return repository.findAll();
    }

    public void deleteLessonVisitById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Посещение занятия", id);
        repository.deleteById(id);
    }

    public LessonVisitModel updateLessonVisit(LessonVisitModel model) {
        if (!repository.existsById(model.getId())) throw new NotFoundException("Посещение занятия", model.getId());
        validateReferences(model);
        return repository.save(model);
    }

    private void validateReferences(LessonVisitModel model) {
        if (!lessonModelRepository.existsById(model.getLessonId())) {
            throw new NotFoundException("Урок", model.getLessonId());
        }
        for (Long studentId : model.getStudentIds()) {
            if (!studentModelRepository.existsById(studentId)) {
                throw new NotFoundException("Студент", studentId);
            }
        }
    }
}
