package someshbose.github.io.xlsreader.app.processor;

import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.Sheet;

public interface SheetHandler {

  String getSheetName();

  default boolean canHandle(final Sheet sheet){
    return this.getSheetName().equalsIgnoreCase(Validate.notNull(sheet).getSheetName());
  }

  void processSheet(final Sheet sheet);

}
