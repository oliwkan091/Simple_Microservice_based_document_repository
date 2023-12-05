package com.Microservice_based_document_repository.Gateway.controller;
import com.Microservice_based_document_repository.Gateway.model.DocumentFileTransfer;
import com.Microservice_based_document_repository.Gateway.request.FileTransferUploadIRequestInterface;
import com.Microservice_based_document_repository.Gateway.security.UserLogInPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


/**
 *  Proxy for upload controller in File_transfer
 */
@RestController
@RequestMapping("gateway/upload")
@CrossOrigin(origins = "http://localhost:4200")
public class FileTransferUploadController {

    private final FileTransferUploadIRequestInterface fileTransferUploadIRequestInterface;

    public FileTransferUploadController(FileTransferUploadIRequestInterface fileTransferUploadIRequestInterface) {
        this.fileTransferUploadIRequestInterface = fileTransferUploadIRequestInterface;
    }

    /**
     * Transmits to File_transfer request to upload given file
     * @param multipartFile file to upload
     * @param user authenticates user
     * @return ResponseEntity with HttpStatus.OK if upload was successful or HttpStatus.INTERNAL_SERVER_ERROR if error occurred
     */
    @PostMapping()
    public ResponseEntity<?> uploadFileByName(@RequestParam("files") MultipartFile multipartFile, @AuthenticationPrincipal UserLogInPrincipal user) throws IOException {
        DocumentFileTransfer file = new DocumentFileTransfer(multipartFile, user.getId());
        try {
            return fileTransferUploadIRequestInterface.uploadFileByName(file);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
