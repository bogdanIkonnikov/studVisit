package krefature.studvisit.DAO.mapper;

import krefature.studvisit.repository.entity.Discipline;
import krefature.studvisit.service.model.DisciplineModel;
import org.springframework.stereotype.Component;

@Component
public class DisciplineModelMapper {
    public DisciplineModel toModel(Discipline discipline){
        DisciplineModel model = new DisciplineModel();
        model.setId(discipline.getId());
        model.setName(discipline.getName());
        model.setCreated_at(discipline.getCreated_at());
        model.setUpdated_at(discipline.getUpdated_at());
        return model;
    }
    public Discipline toEntity(DisciplineModel model){
        Discipline discipline = new Discipline();
        discipline.setId(model.getId());
        discipline.setName(model.getName());
        discipline.setCreated_at(model.getCreated_at());
        discipline.setUpdated_at(model.getUpdated_at());
        return discipline;
    }
}
