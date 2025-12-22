package krefature.studvisit.service.service;

import krefature.studvisit.DAO.repository.StudentModelRepository;
import krefature.studvisit.service.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentModelRepository repository;

    public List<StudentModel> getStudentsByGroupId(Long groupId) {
        return repository.getStudentsByGroupId(groupId);
    }

    public StudentModel getStudentById(Long studentId) {
        return repository.findById(studentId);
    }

    public StudentModel addStudent(StudentModel cModel) {
        return repository.save(cModel);
    }

    public StudentModel updateStudent(StudentModel uModel) {
        return repository.save(uModel);
    }

    public void deleteStudent(Long studentId) {
        repository.deleteById(studentId);
    }


}
