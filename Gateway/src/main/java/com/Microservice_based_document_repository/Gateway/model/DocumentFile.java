package com.Microservice_based_document_repository.Gateway.model;
import com.Microservice_based_document_repository.Gateway.enviroment.Varibles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Model of file saved in database
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "files")
public class DocumentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="file_id", nullable = false)
    private UUID fileId;

    @Column(name ="filename", nullable = false)
    private String filename;

    @Column(name ="file_show_name", nullable = false)
    private String fileShowName;

    @Column(name ="description", nullable = false)
    private String description;

    @Column(name ="path_to_file", nullable = false)
    private String pathToFile;

    @Column(name ="send_date", nullable = false)
    private LocalDateTime sendDate;

    @Column(name ="file_size", nullable = false)
    private Long fileSize;

    @Column(name ="file_type", nullable = false)
    private String fileType;

    @Column(name ="owner_id", nullable = false)
    private Long ownerId;

    @Column(name ="search_name", nullable = false)
    private String searchName;

    public DocumentFile(MultipartFile multipartFile, Long ownerId) {

        this.fileId = UUID.randomUUID();
        this.filename = multipartFile.getOriginalFilename();
        this.pathToFile = Varibles.pathToRepo + "\\" + fileId + filename.substring(filename.lastIndexOf("."));
        this.sendDate = LocalDateTime.now();
        this.fileSize = multipartFile.getSize();
        this.fileType = multipartFile.getContentType();
        this.ownerId = ownerId;
        this.searchName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
        this.fileShowName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
    }

    public DocumentFile(MultipartFile multipartFile, Long ownerId, String description) {

        this.fileId = UUID.randomUUID();
        this.filename = multipartFile.getOriginalFilename();
        this.pathToFile = Varibles.pathToRepo + "\\" + fileId + filename.substring(filename.lastIndexOf("."));
        this.sendDate = LocalDateTime.now();
        this.fileSize = multipartFile.getSize();
        this.fileType = multipartFile.getContentType();
        this.ownerId = ownerId;
        this.searchName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
        this.fileShowName = multipartFile.getOriginalFilename().replaceAll("[.][a-zA-z]+","");
        this.description = description;
    }

    public DocumentFile(DocumentFileTransfer documentFileTransfer) {

        this.fileId = UUID.randomUUID();
        this.filename = documentFileTransfer.getFilename();
        this.pathToFile = fileId + filename.substring(filename.lastIndexOf("."));
        this.sendDate = LocalDateTime.now();
        this.fileSize = documentFileTransfer.getFileSize();
        this.fileType = documentFileTransfer.getFileType();
        this.ownerId = documentFileTransfer.getOwnerId();
        this.searchName =documentFileTransfer.getFilename().replaceAll("[.][a-zA-z]+","");
        this.fileShowName = documentFileTransfer.getFilename().replaceAll("[.][a-zA-z]+","");
        this.description = documentFileTransfer.getDescription();
    }

    public void generateSearchName()
    {
        this.searchName = filename.replaceAll("[.][a-zA-z]+","");
    }

    public void generateFileShowName()
    {
        this.fileShowName = filename.replaceAll("[.][a-zA-z]+","");
    }

    public void changeFilename()
    {
        this.filename = this.fileShowName + this.filename.replaceAll(Varibles.regexGetFileExtensionWithoutDot,".");
        generateSearchName() ;
    }
}
