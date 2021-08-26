package someshbose.github.io.xlsreader.app.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import someshbose.github.io.xlsreader.model.shared.ExcelRow;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
public class SheetParser<EXCEL_ROW> {

  private static final Integer HEADER_INDEX = 0;

  private final DataFormatter cellFormat;
  private final ObjectMapper mapper;

  /**
   *
   * @param mapper
   */
  public SheetParser(ObjectMapper mapper){
    this.mapper = mapper;
    cellFormat=new DataFormatter();
  }

  /**
   *
   * @param sheet
   * @param clazz
   * @return
   */
  public List<EXCEL_ROW> parseFile(Sheet sheet, Class<EXCEL_ROW> clazz){

    Iterator<Row> rowIterator = sheet.iterator();
    List<String> rowHeaders = new ArrayList<>();
    List<EXCEL_ROW> dtos = new ArrayList<>();

    while (rowIterator.hasNext()){
      processXlsRow(rowIterator.next(), rowHeaders).ifPresent(values ->{
        try {
          dtos.add(mapper.convertValue(values,clazz));
        }catch (IllegalArgumentException e){
          log.error("Failed to parse line.");

          //publish FileElementValidationFailed DomainEvent
        }
      });
    }

    return dtos;
  }

  /**
   *
   * @param row
   * @param headers
   * @return
   */
  private Optional<Map<String,String>> processXlsRow(Row row, List<String> headers){
    Iterator<Cell> cellIterator = row.cellIterator();
    Map<String,String> cellValues = new LinkedHashMap<>();

    while (cellIterator.hasNext()){
      processXlsCell(cellIterator.next(),headers,cellValues);
    }

    if (cellValues.size() > 0 && headers.size() >= cellValues.size()){
      cellValues.put(ExcelRow.ROW_NUM_KEY, Integer.toString(row.getRowNum()+1));
      cellValues.put(ExcelRow.SHEET_NAME_KEY, row.getSheet().getSheetName());
      return Optional.of(cellValues);
    }

    return Optional.empty();
  }

  /**
   *
   * @param cell
   * @param headers
   * @param values
   */
  private void processXlsCell(Cell cell, List<String> headers, Map<String,String> values) {
    if (cell.getRow().getRowNum() == HEADER_INDEX){
      headers.add(cell.getStringCellValue());
    }else{

      if(cell.getColumnIndex()>= headers.size()|| cell.getCellType()== CellType.BLANK){
        return;
      }
      if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)){
        ZonedDateTime zonedDateTime
            = ZonedDateTime.ofInstant(cell.getDateCellValue().toInstant(), ZoneId.systemDefault());
        values.put(headers.get(cell.getColumnIndex()), DateTimeFormatter.ISO_INSTANT.format(zonedDateTime.withZoneSameLocal(ZoneId.of("UTC"))));
      } else{
        values.put(headers.get(cell.getColumnIndex()),cellFormat.formatCellValue(cell));
      }
    }
  }
}
