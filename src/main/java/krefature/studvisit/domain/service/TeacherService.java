package krefature.studvisit.domain.service;

import krefature.studvisit.common.exceptions.DependentEntityException;
import krefature.studvisit.domain.repository.TeacherModelRepository;
import krefature.studvisit.domain.repository.LessonModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.domain.model.TeacherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherModelRepository repository;
    @Autowired
    private LessonModelRepository lessonModelRepository;

    public List<TeacherModel> getAllTeachers() {
        return repository.findAll();
    }

    public Page<TeacherModel> getTeachersPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public TeacherModel getTeacherById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Преподаватель", id);
        TeacherModel t = repository.findById(id);
        return t;
    }

    public TeacherModel addTeacher(TeacherModel model) {
        if (repository.existsByFIO(model.getFirstName(), model.getMiddleName(), model.getLastName())) {
            throw new IllegalArgumentException("Преподаватель с таким ФИО уже существует.");
        }
        return repository.save(model);
    }

    public TeacherModel editTeacher(TeacherModel model) {
        if (!repository.existsById(model.getId())) throw new NotFoundException("Преподаватель", model.getId());
        if (repository.existsByFIOAndIdNot(model.getFirstName(), model.getMiddleName(), model.getLastName(), model.getId())) {
            throw new IllegalArgumentException("Преподаватель с таким ФИО уже существует.");
        }
        return repository.save(model);
    }

    public void deleteTeacher(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Преподаватель", id);
        if (lessonModelRepository.existsByTeacherId(id)) {
            throw new DependentEntityException("Нельзя удалить преподавателя, пока с ним связаны занятия.");
        }
        TeacherModel t = repository.findById(id);
        repository.delete(t);
    }
}
