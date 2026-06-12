package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.GroupModelMapper;
import krefature.studvisit.domain.repository.GroupModelRepository;
import krefature.studvisit.infrastructure.entity.Group;
import krefature.studvisit.infrastructure.repository.GroupRepository;
import krefature.studvisit.domain.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GroupModelRepoImpl implements GroupModelRepository {
    @Autowired
    GroupRepository repository;
    @Autowired
    GroupModelMapper mapper;

    @Override
    public List<GroupModel> findAll() {
        List<Group> groups = repository.findAll();
        return groups.stream().map(g -> mapper.toModel(g)).collect(Collectors.toList());
    }

    @Override
    public GroupModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }

    @Override
    public GroupModel save(GroupModel model) {
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
