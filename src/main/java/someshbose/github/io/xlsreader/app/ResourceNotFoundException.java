package someshbose.github.io.xlsreader.app;

/**
 * FileUploadException class.
 * 
 * @author sombose
 */
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructor for FileUploadException.
   * 
   * @param message String.
   */
  public ResourceNotFoundException(final String message) {
    super(message);
  }
}
