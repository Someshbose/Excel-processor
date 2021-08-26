package someshbose.github.io.xlsreader.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import someshbose.github.io.xlsreader.model.file.FileStore;

import java.util.Optional;

/**
 * Repository class for FileStore.
 * 
 * @author sombose
 */
@Repository
public interface FileStoreRepository extends CrudRepository<FileStore, Long> {

  /**
   * find By File Content by fileReference Id.
   * 
   * @param fileRefId String
   * @return Optional<FileStore>
   */
  Optional<FileStore> findByFileReferenceId(String fileRefId);

}
