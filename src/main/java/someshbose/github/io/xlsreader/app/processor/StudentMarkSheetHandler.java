package someshbose.github.io.xlsreader.app.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import someshbose.github.io.xlsreader.app.dto.StudentDto;
import someshbose.github.io.xlsreader.app.service.StudentService;

import java.util.List;

@Component
@Slf4j
public class StudentMarkSheetHandler implements SheetHandler {

  @Autowired
  private SheetParser sheetParser;

  @Autowired
  private StudentService studentService;

  @Override public String getSheetName() {
    return "Sheet1";
  }

  @Override
  public void processSheet(Sheet sheet) {
      List<StudentDto> list = sheetParser.parseFile(sheet,StudentDto.class);
      if (list.isEmpty()){
        log.error("sheet is empty!");
        return;
      }
      log.info("Saving to DB.");
      list.stream().forEach(row->studentService.saveData(row));
  }
}
