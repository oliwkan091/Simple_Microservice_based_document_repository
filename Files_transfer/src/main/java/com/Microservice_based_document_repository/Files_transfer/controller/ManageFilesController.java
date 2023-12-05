package com.Microservice_based_document_repository.Files_transfer.controller;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFile;
import com.Microservice_based_document_repository.Files_transfer.service.ManageFilesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API controller responsible for file management.
 * Handles file removal, name change, comment change and search for files by given name
 */
@RestController
@RequestMapping("api/manage")
public class ManageFilesController {

    private final ManageFilesService manageFilesService;

    public ManageFilesController(ManageFilesService manageFilesService) {
        this.manageFilesService = manageFilesService;
    }

    /**
     * Searchs for all files of spevyfic user by userID and returns list of it
     * @param userId which is currently logged user Id
     * @return ResponseEntity<List<DocumentFile>>  with attached list of all user files
     */
    @PostMapping("/{userId}")
    public ResponseEntity<List<DocumentFile>> getAllUserFiles(@PathVariable("userId") Long userId)
    {
        return new ResponseEntity<>(manageFilesService.getAllUserFiles(userId), HttpStatus.OK);
    }

    /**
     * Deletes specific file by fileId
     * @param file DocumentFile file model of document possessing all necessary data to find a specific file
     */
    @DeleteMapping()
    public ResponseEntity<?>deleteCourseById(@RequestBody DocumentFile file)
    {
        try{
            manageFilesService.deleteCourseById(file);
        }catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Searches database for given file name or tag
     * @param name String name is a name of file or file tag
     * @return ResponseEntity<List<DocumentFile>> with attached list of files that match the requirements
     */
    @PostMapping("/search/{name}")
    public ResponseEntity<List<DocumentFile>> search(@PathVariable("name") String name, @RequestBody Long id)
    {
        return new ResponseEntity<>(manageFilesService.search(name, id), HttpStatus.OK);
    }

    /**
     * Saves changes provided to file as filename or file tags
     * @param file DocumentFile file model of file to be saved
     * @return HttpStatus.OK if file was saved successfully or HttpStatus.CONFLICT if not
     */
    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody DocumentFile file)
    {
        return new ResponseEntity<>(manageFilesService.save(file), HttpStatus.OK);
    }

    /**
     * Returns all files of user that is not currently logged by given username. Used for admins
     * @param id Long id of user of whom files are requested
     * @return ResponseEntity with list of files of given user
     */
    @PostMapping("getAllUserFiles")
    public ResponseEntity<?> getAllFilesByUsername(@RequestBody Long id)
    {
        return new ResponseEntity<>(manageFilesService.getAllFilesByUsername(id), HttpStatus.OK);
    }

}
