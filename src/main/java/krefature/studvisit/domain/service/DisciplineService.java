package krefature.studvisit.domain.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.exceptions.DependentEntityException;
import krefature.studvisit.domain.repository.DisciplineModelRepository;
import krefature.studvisit.domain.repository.LessonModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.domain.model.DisciplineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    @Autowired
    private DisciplineModelRepository repository;
    @Autowired
    private LessonModelRepository lessonModelRepository;

    public List<DisciplineModel> getAll() {
        return repository.findAll();
    }

    public DisciplineModel getById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Дисциплина", id);
        }
        DisciplineModel model = repository.findById(id);
        return model;
    }

    public DisciplineModel addDiscipline(DisciplineModel model) {
        return repository.save(model);
    }

    public DisciplineModel updateDiscipline(DisciplineModel model) {
        if (!repository.existsById(model.getId())) {
            throw new NotFoundException("Дисциплина", model.getId());
        }
        return repository.save(model);
    }

    public void deleteDisciplineById(
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Дисциплина", id);
        }
        if (lessonModelRepository.existsByDisciplineId(id)) {
            throw new DependentEntityException("Нельзя удалить дисциплину, пока с ней связаны занятия.");
        }
        repository.deleteById(id);
    }
}
