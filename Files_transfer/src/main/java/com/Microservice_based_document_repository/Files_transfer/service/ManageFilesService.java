package com.Microservice_based_document_repository.Files_transfer.service;
import com.Microservice_based_document_repository.Files_transfer.enviroment.Varibles;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFile;
import com.Microservice_based_document_repository.Files_transfer.repository.FileRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

/**
 * Service logic called by API controller to handle manage requests
 */
@Service
@Transactional
public class ManageFilesService {

    private final FileRepository fileRepository;

    public ManageFilesService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Connects repository to get all files of specific user
     * @param userId ID of user
     * @return List<DocumentFile> list of found files of provided user ID
     */
    public List<DocumentFile> getAllUserFiles(Long userId)
    {
        return fileRepository.findAllFilesByOwnerId(userId);
    }

    /**
     * Callse repository and tries to delete file of specific user nad file ID
     * @param file file model to be deleted
     */
    public void deleteCourseById(DocumentFile file) throws Exception {
        try {
            DocumentFile fileFromDatabase = fileRepository.getDocumentFileByFileIdAndOwnerId(file.getFileId(), file.getOwnerId());
            fileRepository.deleteDocumentFileByFileIdAndOwnerId(file.getFileId(), file.getOwnerId());

            File fileToDelete = new File(Varibles.pathToRepo +  File.separator + fileFromDatabase.getPathToFile());
            fileToDelete.delete();
        }catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * List<DocumentFile> calls repository to find files that matches text provided by user
     * @param name text provided by user to search for
     * @return List<DocumentFile> list of files that matches text provided by user
     */
    public List<DocumentFile> search(String name, Long id)
    {
        return fileRepository.search(name, id);
    }

    /**
     * Calls repository to save changes in file
     * @param file file model to be saved
     * @return DocumentFile if saving was successful and null if error occurred
     */
    public DocumentFile save(DocumentFile file)
    {
        file.changeFilename();
        file.generateSearchName();
        if (fileRepository.updateDocumentFile(file.getFileShowName(),file.getFilename(), file.getSearchName(), file.getFileId(), file.getDescription()) == 1)
        {
            return fileRepository.findByFileId(file.getFileId());
        }else
        {
            return null;
        }
    }

    /**
     * Get all files by specyfic user ID
     * @param id ID of user
     * @return List<DocumentFile> list of files of user
     */
    public List<DocumentFile> getAllFilesByUsername(Long id)
    {
        return fileRepository.findAllFilesByOwnerId(id);
    }
}
