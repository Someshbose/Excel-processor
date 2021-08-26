package someshbose.github.io.xlsreader.model.student;

import lombok.*;
import someshbose.github.io.xlsreader.model.shared.AbstractBaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STUDENT_DATA")
public class StudentData extends AbstractBaseEntity<StudentData> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CORE_FILE_STORE_SEQ")
    private Long id;

    private String name;

    private String subject;
}
