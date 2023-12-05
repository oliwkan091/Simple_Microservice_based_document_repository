package com.Microservice_based_document_repository.Gateway.request;
import com.Microservice_based_document_repository.Gateway.model.DocumentFile;
import com.Microservice_based_document_repository.Gateway.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Transfer manage requests to service configured in openFeign
 */
@FeignClient(
        value = "FilesTransferManage",
        path = "api/manage",
        url = "${course.service.url}",
        configuration = FeignConfiguration.class
)
public interface FileTransferManageRequest {

    /**
     * Transfer request to get all files of specific user by given id
     * @param userId id of user whom files are to be got
     * @return
     */
    @PostMapping("{userId}")
    ResponseEntity<?> getAllUserFiles(@PathVariable("userId") Long userId);

    /**
     * Transfer request to delete file by given DocumentFile model
     * @param file DocumentFile model of file to be deleted
     * @return HttpStatus.OK if file was deleted successfully or HttpSatatus.INTERNAL_SERVER_ERROR if error occurred
     */
    @DeleteMapping()
    public ResponseEntity<?>deleteCourseById(@RequestBody DocumentFile file);

    /**
     * Transfer request to find all files of user that matches given phrase
     * @param name phrase to search by
     * @param id of user whom files are to be searched
     * @return ResponseEntity with list of files that matches given phrase
     */
    @PostMapping("search/{name}")
    public ResponseEntity<?> search(@PathVariable("name") String name, @RequestBody Long id);

    /**
     * Transfer request to save given file
     * @param documentFile file to be saved
     * @return ResponseEntity with HttpStatus.OK if file was saved successfully or HttpStatus.INTERNAL_SERVER_ERROR
     * if error occurred
     */
    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody DocumentFile documentFile);

    /**
     * Transfer request to change username
     * @param user User model with new name to be saved
     * @return ResponseEntity with HttpStatus.OK if file was saved successfully or HttpStatus.INTERNAL_SERVER_ERROR
     */
    @PostMapping("changeName")
    public ResponseEntity<?> changeName(@RequestBody User user);

    /**
     * Transfer request to get list of all files of given user
     * @param id of user whom files are to be returned
     * @return ResponseEntity with list of all user files
     */
    @PostMapping("getAllUserFiles")
    public ResponseEntity<?> getAllFilesByUsername(@RequestBody Long id);
}
