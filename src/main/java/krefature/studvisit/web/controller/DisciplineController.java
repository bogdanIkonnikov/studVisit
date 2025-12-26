package krefature.studvisit.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.service.service.DisciplineService;
import krefature.studvisit.web.dto.discipline.DisciplineRequest;
import krefature.studvisit.web.dto.discipline.DisciplineResponse;
import krefature.studvisit.web.mapper.DisciplineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/disciplines")
public class DisciplineController {
    @Autowired
    private DisciplineService service;
    @Autowired
    private DisciplineMapper mapper;


    @GetMapping("")
    public List<DisciplineResponse> getAll(){
        return service.getAll().stream().map(d -> mapper.toResponse(d)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DisciplineResponse getById(
            @PathVariable
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id){
        return mapper.toResponse(service.getById(id));
    }

    @PostMapping("/add")
    public DisciplineResponse add(@Valid @RequestBody DisciplineRequest request) {
        return mapper.toResponse(service.addDiscipline(mapper.toModel(request)));
    }

    @PutMapping("/{id}/edit")
    public DisciplineResponse edit(
            @PathVariable
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id,
            @Valid @RequestBody DisciplineRequest request) {
        return mapper.toResponse(service.updateDiscipline(mapper.toModel(request, id)));
    }

    @DeleteMapping("/{id}/delete")
    public void delete(
            @PathVariable
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id) {
        service.deleteDisciplineById(id);
    }
}
