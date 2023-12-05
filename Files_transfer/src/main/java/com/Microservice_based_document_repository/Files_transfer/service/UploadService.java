package com.Microservice_based_document_repository.Files_transfer.service;
import com.Microservice_based_document_repository.Files_transfer.enviroment.Varibles;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFile;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFileTransfer;
import com.Microservice_based_document_repository.Files_transfer.repository.FileRepository;
import org.springframework.stereotype.Service;
import java.io.*;

/**
 * Service logic called by API controller to handle upload requests
 */
@Service
public class UploadService {
    private final FileRepository fileRepository;
    public UploadService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Callse reposortory to save uplouded file
     * @param documentFileTransfer model of file to be saved with binary data
     * @return DocumentFile if saving was successful or null if error occured
     */
    public DocumentFile uploadFile(DocumentFileTransfer documentFileTransfer) throws Exception {

        DocumentFile documentFile = new DocumentFile(documentFileTransfer);
        DocumentFile savedFile = new DocumentFile();

        try {
            savedFile = fileRepository.save(documentFile);
        }catch (Exception e)
        {
            throw new Exception("Error while saving to database: " + e.getMessage());
        }

        try
        {
            File fileToSave = new File(Varibles.pathToRepo + File.separator + documentFile.getPathToFile());
            FileOutputStream fileStreamToSave = new FileOutputStream(fileToSave);
            fileStreamToSave.write(documentFileTransfer.getFile());
            fileStreamToSave.close();

        }catch (IOException e)
        {
            fileRepository.deleteById(documentFile.getId());
            throw new Exception("Error while file: " + e.getMessage());
        }
        return savedFile;
    }
}
