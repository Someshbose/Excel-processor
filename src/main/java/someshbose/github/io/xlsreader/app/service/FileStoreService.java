package someshbose.github.io.xlsreader.app.service;

import someshbose.github.io.xlsreader.app.dto.FileStoreDto;
import someshbose.github.io.xlsreader.app.service.messaging.FileProcessedDomainEvent;
import someshbose.github.io.xlsreader.model.file.FileStore;

/**
 * FileStoreService interface.
 * 
 * @author sombose
 */
public interface FileStoreService {

  /**
   * method for saving content.
   * 
   * @param dto FileStoreDto
   * @return String
   */
  String saveFile(FileStoreDto dto);

  /**
   * method for fetching file.
   * 
   * @param fileRefId String.
   * @return FileStore
   */
  FileStore getFile(String fileRefId);

  /**
   * update FileStore Status.
   * 
   * @param domainEvent FileStatusMessageEvent
   */
  void updateFileStatus(FileProcessedDomainEvent domainEvent);
}
