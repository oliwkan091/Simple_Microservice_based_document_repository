package com.Microservice_based_document_repository.Gateway.controller;

import com.Microservice_based_document_repository.Gateway.request.FileTransferDownloadRequestInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


/**
 * Proxy for download controller in File_transfer, checks if user should be given possibility to download files
 */
@RestController
@RequestMapping("gateway/download")
@CrossOrigin(origins = "http://localhost:4200")
public class FileTransferDownloadController {

    public final FileTransferDownloadRequestInterface fileTransferDownloadRequestInterface;

    public FileTransferDownloadController(FileTransferDownloadRequestInterface fileTransferDownloadRequestInterface) {
        this.fileTransferDownloadRequestInterface = fileTransferDownloadRequestInterface;
    }

    /**
     * Transmits download request to File_transfer conteroller
     * @param filename
     * @return
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<byte[]> downloadFileByName(@PathVariable("uuid") UUID filename) {

        return fileTransferDownloadRequestInterface.downloadFileByName(filename);
    }
}
