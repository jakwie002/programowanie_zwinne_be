package com.project.controller;

import com.project.model.Plik;
import com.project.model.Projekt;
import com.project.repository.PlikiRepository;
import com.project.repository.ProjektRepository;
import com.project.service.PlikService;
import com.project.validation.FileStorageException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController    // przez kontener Springa REST-owy kontroler obsługujący sieciowe żądania
@RequestMapping("/api")
@AllArgsConstructor
public class PlikRestController {
    private final PlikService fileStorageService;

    private final ProjektRepository projektRepository;
    private final PlikiRepository plikiRepository;


    @PostMapping(path = "/uploadFile", consumes = {"multipart/form-data", MediaType.MULTIPART_FORM_DATA_VALUE, "application/x-www-form-urlencoded"})
    public Plik uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("projektId") Integer projektId) throws FileStorageException {
        Projekt projekt = projektRepository.findById(projektId).orElseThrow();
        return fileStorageService.storeFile(file, projekt);
    }

    @GetMapping("/pliki/{projektId}")
    public ResponseEntity<List<Plik>> getPlikiForProjekt(@PathVariable Integer projektId) {
        Projekt projekt = projektRepository.getReferenceById(projektId);
        List<Plik> pliki = fileStorageService.getPlikiByProjekt(projekt);
        return ResponseEntity.ok(pliki);
    }

    @DeleteMapping("/pliki/{plikId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Integer plikId) {
        fileStorageService.deleteFile(plikId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws FileNotFoundException {
        // Ścieżka do pliku na dysku
        String fileBasePath = fileStorageService.getFilePath(fileName);
        Plik plik = plikiRepository.findByFileName(fileName);
        Path path = Paths.get(plik.getFullFilePath());
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
