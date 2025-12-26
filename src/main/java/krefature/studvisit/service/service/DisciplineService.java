package krefature.studvisit.service.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.DAO.repository.DisciplineModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.service.model.DisciplineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    @Autowired
    private DisciplineModelRepository repository;

    public List<DisciplineModel> getAll() {
        return repository.findAll();
    }

    public DisciplineModel getById(Long id) {
        DisciplineModel model = repository.findById(id);
        if (model == null) {
            throw new NotFoundException("Дисциплина", id);
        }
        return model;
    }

    public DisciplineModel addDiscipline(DisciplineModel model) {
        return repository.save(model);
    }

    public DisciplineModel updateDiscipline(DisciplineModel model) {
        if (repository.findById(model.getId()) == null) {
            throw new NotFoundException("Дисциплина", model.getId());
        }
        return repository.save(model);
    }

    public void deleteDisciplineById(
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id) {
        if (repository.findById(id) == null) {
            throw new NotFoundException("Дисциплина", id);
        }
        repository.deleteById(id);
    }
}
