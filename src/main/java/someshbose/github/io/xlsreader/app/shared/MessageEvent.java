package someshbose.github.io.xlsreader.app.shared;

import java.time.Instant;

/**
 * MessageEvent class.
 * 
 * @author sombose
 */
public interface MessageEvent {

  /**
   * returns EventDate.
   * 
   * @return Instant
   */
  Instant getEventDate();

  /**
   * returns EventName.
   * 
   * @return String
   */
  String getEventName();
}
