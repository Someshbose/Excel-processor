package someshbose.github.io.xlsreader.app.service.messaging;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import someshbose.github.io.xlsreader.model.file.FileUploadedStatus;

@Getter
public class FileProcessedDomainEvent extends ApplicationEvent {

  private String fileRedId;
  private FileUploadedStatus status;

  public FileProcessedDomainEvent(Object source, String id, FileUploadedStatus status) {
    super(source);
    this.fileRedId=id;
    this.status=status;
  }

}
