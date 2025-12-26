package krefature.studvisit.service.service;

import krefature.studvisit.DAO.repository.GroupModelRepository;
import krefature.studvisit.DAO.repository.StudentModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.service.model.GroupModel;
import krefature.studvisit.service.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentModelRepository repository;
    @Autowired
    private GroupModelRepository groupModelRepository;

    public List<StudentModel> getStudentsByGroupId(Long groupId) {
        if (groupModelRepository.findById(groupId) == null) throw new NotFoundException("Группа", groupId);
        return repository.getStudentsByGroupId(groupId);
    }

    public StudentModel getStudentById(Long studentId) {
        StudentModel model = repository.findById(studentId);
        if (model == null) throw new NotFoundException("Студент", studentId);
        return model;
    }

    public StudentModel addStudent(StudentModel cModel) {
        return repository.save(cModel);
    }

    public StudentModel updateStudent(StudentModel uModel) {
        StudentModel model = repository.findById(uModel.getId());
        if (model == null) throw new NotFoundException("Студент", uModel.getId());
        return repository.save(uModel);
    }

    public void deleteStudent(Long studentId) {
        if (repository.findById(studentId) == null) throw new NotFoundException("Студент", studentId);
        repository.deleteById(studentId);
    }


}
