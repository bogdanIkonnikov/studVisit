package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.StudentModelMapper;
import krefature.studvisit.domain.repository.StudentModelRepository;
import krefature.studvisit.infrastructure.entity.Student;
import krefature.studvisit.infrastructure.repository.StudentRepository;
import krefature.studvisit.domain.model.StudentModel;
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
        return mapper.toModel(repository.findById(studentId).orElse(null));
    }

    @Override
    public StudentModel save(StudentModel cModel) {
        return mapper.toModel(repository.save(mapper.toEntity(cModel)));
    }

    @Override
    public void deleteById(Long studentId) {
        repository.deleteById(studentId);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByFIO(String f, String i, String o) {
        return repository.existsByFIO(f, i, o);
    }

    @Override
    public boolean existsByFIOAndIdNot(String f, String i, String o, Long id) {
        return repository.existsByFIOAndIdNot(f, i, o, id);
    }
}
