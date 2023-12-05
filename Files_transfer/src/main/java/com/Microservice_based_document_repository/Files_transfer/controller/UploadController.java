package com.Microservice_based_document_repository.Files_transfer.controller;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFileTransfer;
import com.Microservice_based_document_repository.Files_transfer.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API controller responsible for uploading files
 */
@RestController
@RequestMapping("api/upload")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService)
    {
        this.uploadService = uploadService;
    }

    /**
     * Uploads file by its name
     * @param multipartFile DocumentFileTransfer multipartFile model of binary file with its metadata
     * @return HttpStatus.OK when files is uploaded successfully or HttpStatus.INTERNAL_SERVER_ERROR if any error occured
     */
    @PostMapping()
    public ResponseEntity<?> uploadFileByName(@RequestBody DocumentFileTransfer multipartFile)
    {
        try
        {
            return new ResponseEntity<>(uploadService.uploadFile(multipartFile), HttpStatus.OK);

        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
