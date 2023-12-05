package com.Microservice_based_document_repository.Gateway.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 *  Model of file similar with that saved in database with binary file
 */
@Getter
@NoArgsConstructor
public class DocumentFileTransfer {

    private String filename;
    private Long fileSize;
    private String description;
    private String fileType;
    private Long ownerId;
    private byte[] file;

    public DocumentFileTransfer(MultipartFile multipartFile, Long ownerId) throws IOException {

        this.filename = multipartFile.getOriginalFilename();
        this.fileSize = multipartFile.getSize();
        this.fileType = multipartFile.getContentType();
        this.ownerId = ownerId;
        this.file = multipartFile.getBytes();
    }

    public DocumentFileTransfer(MultipartFile multipartFile, Long ownerId, String description) throws IOException {

        this.filename = multipartFile.getOriginalFilename();
        this.fileSize = multipartFile.getSize();
        this.fileType = multipartFile.getContentType();
        this.ownerId = ownerId;
        this.file = multipartFile.getBytes();
        this.description = description;
    }
}
