package com.Microservice_based_document_repository.Gateway.request;
import com.Microservice_based_document_repository.Gateway.model.DocumentFileTransfer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * Transfer upload requests to service configured in openFeign
 */
@FeignClient(
        value = "FilesTransferUpload",
        path = "api/upload",
        url = "${course.service.url}",
        configuration = FeignConfiguration.class
)
public interface FileTransferUploadIRequestInterface {

    /**
     * Transfer request to upload given file
     * @param documentFile DocumentFile model with binary file to be saved
     * @return ResponseEntity with HttpStatus.OK if file was saved successfully or HttpStatus.INTERNAL_SERVER_ERROR
     */
    @PostMapping()
    ResponseEntity<?> uploadFileByName(DocumentFileTransfer documentFile);
}
