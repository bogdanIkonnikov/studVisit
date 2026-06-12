package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.LessonVisitModelMapper;
import krefature.studvisit.domain.repository.LessonVisitModelRepository;
import krefature.studvisit.domain.model.LessonVisitModel;
import krefature.studvisit.infrastructure.repository.LessonVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LessonVisitModelRepoImpl implements LessonVisitModelRepository {
    @Autowired
    private LessonVisitRepository repository;
    @Autowired
    private LessonVisitModelMapper mapper;

    @Override
    public LessonVisitModel save(LessonVisitModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public LessonVisitModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }

    @Override
    public List<LessonVisitModel> findAll() {
        return repository.findAll().stream()
                .map(l -> mapper.toModel(l))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public LessonVisitModel findByLessonId(Long lessonId) {
        return mapper.toModel(repository.findByLessonId(lessonId));
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<LessonVisitModel> findAllByStudentId(Long studentId) {
        return repository.findAllByStudentId(studentId).stream().map(v -> mapper.toModel(v)).collect(Collectors.toList());
    }
}
