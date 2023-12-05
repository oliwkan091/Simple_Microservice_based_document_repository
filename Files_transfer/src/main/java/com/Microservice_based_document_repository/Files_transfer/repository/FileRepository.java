package com.Microservice_based_document_repository.Files_transfer.repository;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

/**
 * Representation of methods that are being used to connect ot database
 */
public interface FileRepository extends JpaRepository<DocumentFile, Long> {

    /**
     * Finds file by its ID
     * @param fileId id of file
     * @return file metadata serialized to DocumentFile model
     */
    DocumentFile findByFileId(UUID fileId);

    /**
     * Gets all files of given user
     * @param ownerId ID of user to find files from
     * @return List<DocumentFile> list of files of given user
     */
    List<DocumentFile> findAllFilesByOwnerId(Long ownerId);

    /**
     * Gets form database specyfic file with given owner ID and file ID
     * @param fileId ID of file
     * @param ownerId ID od owen
     * @return DocumentFile Found file
     */
    DocumentFile getDocumentFileByFileIdAndOwnerId(UUID fileId,Long ownerId);

    /**
     * Deletes specific file by owner ID and file ID
     * @param fileId ID of file
     * @param ownerId ID of owner
     * @return type int, 1 if file was deleted successfully and 0 i error occurred
     */
    int deleteDocumentFileByFileIdAndOwnerId(UUID fileId,Long ownerId);

    /**
     * Full search query, searches database for a files that matches given description.
     * Uses SqlFunctionsMetadataBuilderContributor class
     * @param description of file which is being looked for
     * @return list of files that matches description
     */
    @Query("SELECT f FROM DocumentFile f WHERE fullSearch(:owner_id, :description, :description) = true")
    List<DocumentFile> search(@Param("description") String description, @Param("owner_id") Long ownerId);

    /**
     * Saves changes in given file
     * @param fileShowName changed showName to be saved
     * @param filename changed name to be saved
     * @param searchName changed searchName to be saved
     * @param fileId changed name to be saved
     * @param description changed name to be saved
     * @return type int, 1 if file was updated successfully and 0 i error occurred
     */
    @Modifying
    @Query("UPDATE DocumentFile " +
            "SET fileShowName = :fileShowName," +
            "filename = :filename," +
            "searchName = :searchName, " +
            "description = :description " +
            "WHERE fileId = :fileId")

    int updateDocumentFile(@Param("fileShowName") String fileShowName,
                           @Param("filename") String filename,
                           @Param("searchName") String searchName,
                           @Param("fileId") UUID fileId,
                           @Param("description") String description);
}


