package someshbose.github.io.xlsreader.model.shared;

public interface ExcelRow {

  String ROW_NUM_KEY = "rowNum" ;

  String SHEET_NAME_KEY = "sheetName" ;

  int getRowNum();

  String getSheetName();
}
