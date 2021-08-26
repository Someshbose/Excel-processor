package someshbose.github.io.xlsreader.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import someshbose.github.io.xlsreader.app.dto.StudentDto;
import someshbose.github.io.xlsreader.model.repo.StudentDataRepository;
import someshbose.github.io.xlsreader.model.student.StudentData;

@Service
public class StudentService {

  @Autowired
  private StudentDataRepository studentDataRepository;

  public void saveData(StudentDto dto){
    StudentData studentData = constructEntityFromDto(dto);
    studentDataRepository.save(studentData);
  }

  private StudentData constructEntityFromDto(StudentDto dto){
    return StudentData.builder()
        .id(dto.getId())
        .name(dto.getStudentName())
        .subject(dto.getStudentName()).build();
  }
}
