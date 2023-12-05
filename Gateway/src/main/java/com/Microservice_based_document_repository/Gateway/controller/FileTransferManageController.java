package com.Microservice_based_document_repository.Gateway.controller;
import com.Microservice_based_document_repository.Gateway.model.DocumentFile;
import com.Microservice_based_document_repository.Gateway.model.User;
import com.Microservice_based_document_repository.Gateway.request.FileTransferManageRequest;
import com.Microservice_based_document_repository.Gateway.security.UserLogInPrincipal;
import com.Microservice_based_document_repository.Gateway.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * Proxy for manage file controller in File_transfer, checks if user should be given possibility to manage files
 */
@RestController
@RequestMapping("gateway/manage")
@CrossOrigin(origins = "http://localhost:4200")
public class FileTransferManageController {

    private final FileTransferManageRequest fileTransferManageRequest;

    private final UserService userService;

    public FileTransferManageController(FileTransferManageRequest fileTransferManageRequest, UserService userService) {
        this.fileTransferManageRequest = fileTransferManageRequest;
        this.userService = userService;
    }

    /**
     * Transmits to File_transfer request to get list of all files of currently logged user
     * @param user authentication, authenticate user
     * @return ResponseEntity with list of all files attached
     */
    @PostMapping()
    public ResponseEntity<?> getAllUserFiles(@AuthenticationPrincipal UserLogInPrincipal user)
    {
        return fileTransferManageRequest.getAllUserFiles(user.getId());
    }

    /**
     * Transmits to File_transfer request to get list of all files of user
     * @param fileId ID of file to be deleted
     * @param user authenticates user
     * @return ResponseEntity with HttpStatus.OK if file was deleted or HttpStatus.INTERNAL_SERVER_ERROR if error occurred
     */
    @DeleteMapping("/{fileId}")
    public ResponseEntity<?>deleteCourseById(@PathVariable UUID fileId, @AuthenticationPrincipal UserLogInPrincipal user)
    {
        DocumentFile file = new DocumentFile();
        file.setOwnerId(user.getId());
        file.setFileId(fileId);
        return fileTransferManageRequest.deleteCourseById(file);
    }

    /**
     * Transmits to File_transfer request to search database to find all files of user that matches given phrase
     * @param name phrase to be found
     * @param user authenticates user
     * @return ResponseEntity with all search results
     */
    @GetMapping("/{name}")
    public ResponseEntity<?> search(@PathVariable String name, @AuthenticationPrincipal UserLogInPrincipal user)
    {
        return fileTransferManageRequest.search(name, user.getId());
    }


    /**
     * Transmits to File_transfer request to save given edited file
     * @param file edited file to be save
     * @param user authenticates user
     * @return HttpStatus.OK if file was saved successfully or HttpStatus.INTERNAL_SERVER_ERROR if error occurred
     */
    @PostMapping("save")
    public ResponseEntity<?> saveEditedFile(@RequestBody DocumentFile file, @AuthenticationPrincipal UserLogInPrincipal user)
    {
        return fileTransferManageRequest.save(file);
    }

    /**
     * Transmits to File_transfer request to get list of all files of given user
     * @param name name of user from whom to get all files
     * @return ResponseEntity with list of all files of user
     */
    @PostMapping("getAllFilesByUsername")
    public ResponseEntity<?> getAllFilesByUsername(@RequestBody String name)
    {
        User user = userService.findUserByName(name).get();
        return fileTransferManageRequest.getAllFilesByUsername(user.getId());
    }
}
