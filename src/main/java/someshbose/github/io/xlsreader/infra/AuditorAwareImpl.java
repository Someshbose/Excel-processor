package someshbose.github.io.xlsreader.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * AuditorAware Implementation.
 * 
 * @author sombose
 */
@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

  private final Environment env;

  /**
   * AuditorAwareImpl constructor,
   * 
   * @param env Environment.
   */
  @Autowired
  public AuditorAwareImpl(Environment env) {
    this.env = env;
  }

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(env.getRequiredProperty("service.audit.user"));
  }

}
