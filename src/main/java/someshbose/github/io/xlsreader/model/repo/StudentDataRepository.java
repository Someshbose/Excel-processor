package someshbose.github.io.xlsreader.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import someshbose.github.io.xlsreader.model.student.StudentData;

@Repository
public interface StudentDataRepository extends CrudRepository<StudentData, Long> {
}
