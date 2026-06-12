package krefature.studvisit.DAO.mapper;

import krefature.studvisit.infrastructure.entity.Student;
import krefature.studvisit.infrastructure.repository.GroupRepository;
import krefature.studvisit.domain.model.StudentModel;
import org.springframework.stereotype.Component;

@Component
public class StudentModelMapper {
    private final GroupRepository groupRepository;

    public StudentModelMapper(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public StudentModel toModel(Student student){
        if (student == null) return null;
        StudentModel studentModel = new StudentModel();
        studentModel.setId(student.getId());
        studentModel.setFirstName(student.getFirstName());
        studentModel.setLastName(student.getLastName());
        studentModel.setMiddleName(student.getMiddleName());
        studentModel.setStatus(student.getStatus());
        if(student.getGroup()!=null){
            studentModel.setGroupId(student.getGroup().getId());
        }
        if (student.getLessonVisit() != null) {
            studentModel.setLessonVisitIds(student.getLessonVisit().stream()
                    .map(lessonVisit -> lessonVisit.getId())
                    .toList());
        }
        studentModel.setCreated_at(student.getCreated_at());
        studentModel.setUpdated_at(student.getUpdated_at());
        return studentModel;
    }
    public Student toEntity(StudentModel studentModel){
        if (studentModel == null) return null;
        Student student = new Student();
        student.setId(studentModel.getId());
        student.setFirstName(studentModel.getFirstName());
        student.setLastName(studentModel.getLastName());
        student.setMiddleName(studentModel.getMiddleName());
        student.setStatus(studentModel.getStatus());
        student.setGroup(studentModel.getGroupId() == null ? null : groupRepository.findById(studentModel.getGroupId()).orElse(null));
        student.setCreated_at(studentModel.getCreated_at());
        student.setUpdated_at(studentModel.getUpdated_at());
        return student;
    }
}
