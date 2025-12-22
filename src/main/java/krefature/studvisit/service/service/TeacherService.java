package krefature.studvisit.service.service;

import krefature.studvisit.DAO.repository.TeacherModelRepository;
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
        return repository.findById(id);
    }

    public TeacherModel addTeacher(TeacherModel model) {
        return repository.save(model);
    }

    public TeacherModel editTeacher(TeacherModel model) {
        TeacherModel m = repository.findById(model.getId());
        m.setFirstName(model.getFirstName());
        m.setLastName(model.getLastName());
        m.setMiddleName(model.getMiddleName());
        return repository.save(m);
    }

    public void deleteTeacher(Long id) {
        TeacherModel t = repository.findById(id);
        repository.delete(t);
    }
}
