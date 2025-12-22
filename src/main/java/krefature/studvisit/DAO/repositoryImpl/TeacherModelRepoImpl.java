package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.TeacherModelMapper;
import krefature.studvisit.DAO.repository.TeacherModelRepository;
import krefature.studvisit.repository.entity.Teacher;
import krefature.studvisit.repository.repository.TeacherRepository;
import krefature.studvisit.service.model.TeacherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TeacherModelRepoImpl implements TeacherModelRepository {
    @Autowired
    private TeacherRepository repository;
    @Autowired
    private TeacherModelMapper mapper;

    @Override
    public List<TeacherModel> findAll() {
        return repository.findAll().stream().map(t -> mapper.toModel(t)).collect(Collectors.toList());
    }

    @Override
    public TeacherModel findById(Long id) {
        return mapper.toModel(repository.findById(id).get());
    }

    @Override
    public TeacherModel save(TeacherModel model) {
        Teacher t = mapper.toEntity(model);
        t = repository.save(t);
        return mapper.toModel(t);
    }

    @Override
    public void delete(TeacherModel t) {
        repository.deleteById(t.getId());
    }
}
