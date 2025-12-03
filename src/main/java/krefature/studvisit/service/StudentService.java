package krefature.studvisit.service;

import krefature.studvisit.dto.student.CreateStudentRequest;
import krefature.studvisit.dto.student.StudentResponse;
import krefature.studvisit.dto.student.EditStudentRequest;
import krefature.studvisit.entity.Student;
import krefature.studvisit.repository.GroupRepository;
import krefature.studvisit.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<StudentResponse> getStudentsByGroup(Long groupId) {
        List<Student> students = studentRepository.getAllByGroup(groupId);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            StudentResponse response = new StudentResponse(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getGroup().getName());
            studentResponses.add(response);
        }
        return studentResponses;
    }

    public StudentResponse getStudentById(Long studentId) {
        Student student = studentRepository.getById(studentId);
        return new StudentResponse(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getGroup().getName());
    }

    public Long addStudent(CreateStudentRequest request) {
        Student student = new Student(request.getFirstName(), request.getMiddleName(), request.getLastName(), request.getStatus() , groupRepository.findById(request.getGroupId()).get());
        return studentRepository.add(student).getId();
    }

    public StudentResponse updateStudent(EditStudentRequest request) {
        Student student = studentRepository.getById(request.getId());
        student.setStatus(request.getStatus());
        student.setUpdated_at(LocalDate.now().toString());
        studentRepository.update(student);
        return new StudentResponse(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getGroup().getName());
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }


}
