package krefature.studvisit.service.service;

import krefature.studvisit.DAO.repository.TeacherModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.service.model.TeacherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    TeacherModelRepository repository;

    public List<TeacherModel> getAllTeachers() {
        return repository.findAll();
    }

    public TeacherModel getTeacherById(Long id) {
        TeacherModel t = repository.findById(id);
        if (t == null) throw new NotFoundException("Преподаватель", id);
        return t;
    }

    public TeacherModel addTeacher(TeacherModel model) {
        return repository.save(model);
    }

    public TeacherModel editTeacher(TeacherModel model) {
        if (repository.findById(model.getId()) == null) throw new NotFoundException("Преподаватель", model.getId());
        return repository.save(model);
    }

    public void deleteTeacher(Long id) {
        TeacherModel t = repository.findById(id);
        if (t == null) throw new NotFoundException("Преподаватель", id);
        repository.delete(t);
    }
}
