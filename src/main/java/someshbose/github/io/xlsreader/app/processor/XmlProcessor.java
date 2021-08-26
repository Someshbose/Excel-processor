package someshbose.github.io.xlsreader.app.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import someshbose.github.io.xlsreader.app.InvalidFileContentException;
import someshbose.github.io.xlsreader.app.service.FileStoreService;
import someshbose.github.io.xlsreader.app.service.messaging.FileProcessedDomainEvent;
import someshbose.github.io.xlsreader.app.service.messaging.FileUploadedDomainEvent;
import someshbose.github.io.xlsreader.model.file.FileStore;
import someshbose.github.io.xlsreader.model.file.FileUploadedStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
@Slf4j
public class XmlProcessor {

  @Autowired
  private Collection<SheetHandler> sheetHandlers;

  @Autowired
  private FileStoreService service;

  @Autowired
  private ApplicationEventPublisher publisher;

  /**
   * Handling of Domain Event
   *
   * @param fileUploadedDomainEvent fileUploadedevent
   */
  @Async
  @EventListener
  public void handleFileRecievedDomainEvent(FileUploadedDomainEvent fileUploadedDomainEvent) {
    log.info("Handling FileReceived Event ");
    processFile(service.getFile(fileUploadedDomainEvent.getFileRefId()));
  }

  /**
   *
   * @param fileDto
   */
  public void processFile(final FileStore fileDto) {
    log.info("Decoding file content for reference id {}",fileDto.getFileReferenceId());
    InputStream stream = new ByteArrayInputStream(Base64.decodeBase64(fileDto.getFileContent()));
    String processingMessage;
    try(Workbook workbook = new XSSFWorkbook(stream)){

      final Stream<Sheet> sheets = StreamSupport.stream(workbook.spliterator(), false);
      final long errorCount = sheets.mapToLong(sheet->processSheet(sheet)).sum();

      processingMessage = (errorCount == 0)? StringUtils.EMPTY : String.format("%d errors found on Excel file.",errorCount);

    }catch (IOException | NotOfficeXmlFileException e){
      processingMessage = MessageFormat.format("Can't open excel workbook {}",stream);
      log.error(processingMessage);
    }

    log.info("Publishing processed Event ");
    FileUploadedStatus status ;
    if (processingMessage!=null){
      status = FileUploadedStatus.ERROR;
    }else{
      status = FileUploadedStatus.PROCESSED;
    }

    publisher.publishEvent(new FileProcessedDomainEvent(this, fileDto.getFileReferenceId(), status));
  }

  private long processSheet(final Sheet sheet){
    final Optional<SheetHandler> handler = sheetHandlers.stream().filter(h-> h.canHandle(sheet)).findAny();
    long errorCount = 0L;
    log.info("handler present {} ",handler.isEmpty());
    try {
      if (handler.isPresent()){
        log.info("Processing Excel Sheet '{}'.",sheet.getSheetName()) ;
        handler.get().processSheet(sheet);
      } else {
        //publish SheetNameNotSupportedDomain Event.
      }
    } catch(InvalidFileContentException e){
      errorCount = e.getMessage().split(",").length;
      log.error("{} error occuered when processing excel sheet '{}' ",errorCount,sheet.getSheetName());
    }
    return errorCount;
  }
}
