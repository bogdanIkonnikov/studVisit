package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.DisciplineModelMapper;
import krefature.studvisit.domain.repository.DisciplineModelRepository;
import krefature.studvisit.infrastructure.repository.DisciplineRepository;
import krefature.studvisit.domain.model.DisciplineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DisciplineModelRepoImpl implements DisciplineModelRepository {
    @Autowired
    private DisciplineRepository repository;
    @Autowired
    private DisciplineModelMapper mapper;


    @Override
    public List<DisciplineModel> findAll() {
        return repository.findAll().stream().map(d -> mapper.toModel(d)).collect(Collectors.toList());
    }

    @Override
    public DisciplineModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }

    @Override
    public DisciplineModel save(DisciplineModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
