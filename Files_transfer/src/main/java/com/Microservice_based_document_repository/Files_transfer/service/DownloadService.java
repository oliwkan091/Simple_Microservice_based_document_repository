package com.Microservice_based_document_repository.Files_transfer.service;
import com.Microservice_based_document_repository.Files_transfer.enviroment.Varibles;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFile;
import com.Microservice_based_document_repository.Files_transfer.repository.FileRepository;
import org.springframework.stereotype.Service;
import javax.naming.NameNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.UUID;
import static java.nio.file.Paths.get;

/**
 * Service logic called by API controller to handle download requests
 */
@Service
public class DownloadService {
    private final FileRepository fileRepository;

    public DownloadService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Checks if filename is valid and calls repository to connect to database
     * @param fileId
     * @return DocumentFile model with data found in database
     */
    public DocumentFile downloadFileByName(UUID fileId) throws FileNotFoundException, NameNotFoundException, MalformedURLException {
        DocumentFile file = fileRepository.findByFileId(fileId);
        file.setPathToFile(Varibles.pathToRepo + File.separator + file.getPathToFile());
        if (file == null)
        {
            throw new FileNotFoundException("File does not exist");
        }

        if(!Files.exists(get(file.getPathToFile())))
        {
            throw new NameNotFoundException(file.getFilename() + ": does not exist");
        }
        return file;
    }
}
