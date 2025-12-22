package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.StudentModelMapper;
import krefature.studvisit.DAO.repository.StudentModelRepository;
import krefature.studvisit.repository.entity.Student;
import krefature.studvisit.repository.repository.StudentRepository;
import krefature.studvisit.service.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentModelRepoImpl implements StudentModelRepository {
    @Autowired
    private StudentRepository repository;
    @Autowired
    private StudentModelMapper mapper;

    @Override
    public List<StudentModel> getStudentsByGroupId(Long groupId) {
        List<Student> students = repository.getStudentsByGroupId(groupId);
        return students.stream().map(s -> mapper.toModel(s)).collect(Collectors.toList());
    }

    @Override
    public StudentModel findById(Long studentId) {
        return mapper.toModel(repository.findById(studentId).get());
    }

    @Override
    public StudentModel save(StudentModel cModel) {
        return mapper.toModel(repository.save(mapper.toEntity(cModel)));
    }

    @Override
    public void deleteById(Long studentId) {
        repository.deleteById(studentId);
    }
}
