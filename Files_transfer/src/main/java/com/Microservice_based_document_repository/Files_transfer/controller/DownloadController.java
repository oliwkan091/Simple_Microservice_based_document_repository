package com.Microservice_based_document_repository.Files_transfer.controller;
import com.Microservice_based_document_repository.Files_transfer.model.DocumentFile;
import com.Microservice_based_document_repository.Files_transfer.service.DownloadService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.naming.NameNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static java.nio.file.Paths.get;

/**
 * Api controller that is responsible for downlaoding files
 */
@RestController
@RequestMapping("api/download")
public class DownloadController {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    /**
     * Downloads file by given name as UUID of file
     * @param filename UUID of file send in the end of API link
     * @return ResponseEntity<UrlResource> with attached binary file with metadata
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<UrlResource> downloadFileByName(@PathVariable("uuid") UUID filename)
    {
        try {
            DocumentFile file = downloadService.downloadFileByName(filename);
            UrlResource resource = new UrlResource(get(file.getPathToFile()).toUri());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("file", file.getFilename());
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;file=" + file.getFilename());

            return ResponseEntity.ok().headers(httpHeaders).body(resource);
        }catch(FileNotFoundException e)
        {
            System.out.println("No file on server");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (NameNotFoundException e)
        {
            System.out.println("No file in database");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (IOException e)
        {
            System.out.println("Read exception");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e)
        {
            System.out.println("Error");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
