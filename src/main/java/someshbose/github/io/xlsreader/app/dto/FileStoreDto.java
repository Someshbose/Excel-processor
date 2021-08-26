package someshbose.github.io.xlsreader.app.dto;

import lombok.*;

/**
 * FileStoreDto class.
 * 
 * @author sombose
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileStoreDto {

  private String fileTypeCode;
  private String fileName;
  private String charSet;
  private String fileContent;
  private String fieldSeparator;
  private String correlationId;
  private String submitterEmail;
}
