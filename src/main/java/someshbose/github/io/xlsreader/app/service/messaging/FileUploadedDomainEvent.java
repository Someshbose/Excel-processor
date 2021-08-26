package someshbose.github.io.xlsreader.app.service.messaging;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;

/**
 *
 */
@Getter
public class FileUploadedDomainEvent extends ApplicationEvent {

    private String fileRefId;
    private Instant occuredOn = Instant.now();

    public FileUploadedDomainEvent(Object source,String fileRefId) {
        super(source);
        this.fileRefId = fileRefId;
    }

//    public static FileUploadedDomainEvent of(){
//        return new FileUploadedDomainEvent();
//    }
}
