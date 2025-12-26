package krefature.studvisit.web.mapper;

import krefature.studvisit.service.model.DisciplineModel;
import krefature.studvisit.web.dto.discipline.DisciplineRequest;
import krefature.studvisit.web.dto.discipline.DisciplineResponse;
import org.springframework.stereotype.Component;

@Component
public class DisciplineMapper {
    public DisciplineResponse toResponse(DisciplineModel model){
        DisciplineResponse response = new DisciplineResponse();
        response.setName(model.getName().toString());
        return response;
    }
    public DisciplineModel toModel(DisciplineRequest request){
        DisciplineModel model = new DisciplineModel();
        model.setName(request.getName());
        return model;
    }
    public DisciplineModel toModel(DisciplineRequest request, Long id){
        DisciplineModel model = new DisciplineModel();
        model.setId(id);
        model.setName(request.getName());
        return model;
    }
}
