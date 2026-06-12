package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.TeacherModelMapper;
import krefature.studvisit.domain.repository.TeacherModelRepository;
import krefature.studvisit.infrastructure.entity.Teacher;
import krefature.studvisit.infrastructure.repository.TeacherRepository;
import krefature.studvisit.domain.model.TeacherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return mapper.toModel(repository.findById(id).orElse(null));
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

    @Override
    public Page<TeacherModel> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toModel);
    }
}
