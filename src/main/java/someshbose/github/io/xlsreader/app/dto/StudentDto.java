package someshbose.github.io.xlsreader.app.dto;

import lombok.Getter;
import someshbose.github.io.xlsreader.model.shared.ExcelRow;

@Getter
public class StudentDto implements ExcelRow {

  private long id;
  private String studentName;
  private String subject;

  private int rowNum;
  private String sheetName;

}
