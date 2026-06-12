package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.LessonModelMapper;
import krefature.studvisit.domain.repository.LessonModelRepository;
import krefature.studvisit.infrastructure.repository.LessonRepository;
import krefature.studvisit.domain.model.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LessonModelRepoImpl implements LessonModelRepository {
    @Autowired
    private LessonRepository repository;
    @Autowired
    private LessonModelMapper mapper;

    @Override
    public List<LessonModel> getAllByTeacherIdAndDateBetween(Long teacherId, String dateAfter, String dateBefore) {
        return (repository.findAllByTeacherIdAndDateBetween(teacherId, dateAfter, dateBefore)).stream()
                .map(l -> mapper.toModel(l)).collect(Collectors.toList());
    }

    @Override
    public List<LessonModel> getAllByGroupIdAndDateBetween(Long groupId, String dateAfter, String dateBefore) {
        return (repository.findAllByGroupIdAndDateBetween(groupId, dateAfter, dateBefore)).stream()
                .map(l -> mapper.toModel(l)).collect(Collectors.toList());
    }

    @Override
    public void deleteAllByTeacherId(Long teacherId) {
        repository.deleteAllByTeacherId(teacherId);
    }

    @Override
    public void deleteAllByGroupId(Long groupId) {
        repository.deleteAllByGroupId(groupId);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public LessonModel save(LessonModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public LessonModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByTeacherId(Long teacherId) {
        return repository.existsByTeacherId(teacherId);
    }

    @Override
    public boolean existsByDisciplineId(Long disciplineId) {
        return repository.existsByDisciplineId(disciplineId);
    }

    @Override
    public Page<LessonModel> getPageByTeacherIdAndDateBetween(Long teacherId, String dateAfter, String dateBefore, Pageable pageable) {
        return repository.findAllByTeacherIdAndDateBetween(teacherId, dateAfter, dateBefore, pageable)
                .map(mapper::toModel);
    }

    @Override
    public Page<LessonModel> getPageByGroupIdAndDateBetween(Long groupId, String dateAfter, String dateBefore, Pageable pageable) {
        return repository.findAllByGroupIdAndDateBetween(groupId, dateAfter, dateBefore, pageable)
                .map(mapper::toModel);
    }
}
