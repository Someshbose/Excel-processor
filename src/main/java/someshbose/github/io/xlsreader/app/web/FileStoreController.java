package someshbose.github.io.xlsreader.app.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import someshbose.github.io.xlsreader.app.dto.FileStoreDto;
import someshbose.github.io.xlsreader.app.service.FileStoreService;
import someshbose.github.io.xlsreader.model.file.FileStore;

import java.net.URI;

/**
 * FileStoreController class.
 * 
 * @author sombose
 */
@RestController
public class FileStoreController {

  private FileStoreService service;

  /**
   * constructor for FileStoreController.
   * 
   * @param service FileStoreService
   */
  public FileStoreController(FileStoreService service) {
    this.service = service;
  }

  /**
   * FileUpload Method.
   * 
   * @param dto FileStoreDto
   * @return ResponseEntity
   */
  @PostMapping("/filestore")
  public ResponseEntity upload(@RequestBody final FileStoreDto dto) {
    URI url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(service.saveFile(dto))
        .toUri();
    return ResponseEntity.created(url).build();
  }

  /**
   * getFileContent Method.
   * 
   * @param id String
   * @return {@link ResponseEntity}
   */
  @GetMapping("/findbyId/{id}")
  public ResponseEntity getFile(@PathVariable String id) {
      FileStore response = service.getFile(id);
      return ResponseEntity.ok(response);
  }
}
