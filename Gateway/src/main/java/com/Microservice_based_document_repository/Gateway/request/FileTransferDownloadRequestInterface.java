package com.Microservice_based_document_repository.Gateway.request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

/**
 * Transfer download requests to service configured in openFeign
 */
@FeignClient(
        value = "FilesTransferDownload",
        path = "api/download",
        url = "${course.service.url}",
        configuration = FeignConfiguration.class
)
public interface FileTransferDownloadRequestInterface {

    /**
     * Transfer download request to given service
     * @param filename name of file to be downloaded
     * @return ResponseEntity with binary file attached
     */
    @GetMapping("/{uuid}")
    ResponseEntity<byte[]> downloadFileByName(@PathVariable("uuid") UUID filename);
}

