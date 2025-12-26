package krefature.studvisit.DAO.repositoryImpl;

import krefature.studvisit.DAO.mapper.LessonModelMapper;
import krefature.studvisit.DAO.repository.LessonModelRepository;
import krefature.studvisit.repository.repository.LessonRepository;
import krefature.studvisit.repository.repository.TeacherRepository;
import krefature.studvisit.service.model.LessonModel;
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
    public LessonModel save(LessonModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public LessonModel findById(Long id) {
        return mapper.toModel(repository.findById(id).get());
    }
}
