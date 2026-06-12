package krefature.studvisit.domain.service;

import krefature.studvisit.domain.repository.GroupModelRepository;
import krefature.studvisit.domain.repository.StudentModelRepository;
import krefature.studvisit.domain.repository.LessonVisitModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.common.exceptions.InvalidForeignKeyException;
import krefature.studvisit.domain.model.LessonVisitModel;
import krefature.studvisit.domain.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentModelRepository repository;
    @Autowired
    private GroupModelRepository groupModelRepository;
    @Autowired
    private LessonVisitModelRepository lessonVisitModelRepository;

    public List<StudentModel> getStudentsByGroupId(Long groupId) {
        if (!groupModelRepository.existsById(groupId)) throw new NotFoundException("Группа", groupId);
        return repository.getStudentsByGroupId(groupId);
    }

    public StudentModel getStudentById(Long studentId) {
        if (!repository.existsById(studentId)) throw new NotFoundException("Студент", studentId);
        return repository.findById(studentId);
    }

    public StudentModel addStudent(StudentModel cModel) {
        if (!groupModelRepository.existsById(cModel.getGroupId())) {
            throw new InvalidForeignKeyException(
                    "Группа с ID " + cModel.getGroupId() + " не существует. Невозможно создать студента без существующей группы.",
                    "Student", "groupId", cModel.getGroupId()
            );
        }
        if (repository.existsByFIO(cModel.getFirstName(), cModel.getMiddleName(), cModel.getLastName())) {
            throw new IllegalArgumentException("Студент с таким ФИО уже существует.");
        }
        return repository.save(cModel);
    }

    public StudentModel updateStudent(StudentModel uModel) {
        if (!repository.existsById(uModel.getId())) throw new NotFoundException("Студент", uModel.getId());
        if (!groupModelRepository.existsById(uModel.getGroupId())) {
            throw new InvalidForeignKeyException(
                    "Группа с ID " + uModel.getGroupId() + " не существует. Невозможно обновить студента.",
                    "Student", "groupId", uModel.getGroupId()
            );
        }
        if (repository.existsByFIOAndIdNot(uModel.getFirstName(), uModel.getMiddleName(), uModel.getLastName(), uModel.getId())) {
            throw new IllegalArgumentException("Студент с таким ФИО уже существует.");
        }
        return repository.save(uModel);
    }

    public void deleteStudent(Long studentId) {
        if (!repository.existsById(studentId)) throw new NotFoundException("Студент", studentId);
        List<LessonVisitModel> visits =  lessonVisitModelRepository.findAllByStudentId(studentId);
        for (LessonVisitModel v : visits){
            List<Long> ids = v.getStudentIds();
            ids.remove(studentId);
            v.setStudentIds(ids);
            lessonVisitModelRepository.save(v);
        }
        repository.deleteById(studentId);
    }
}
