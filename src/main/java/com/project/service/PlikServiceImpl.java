package com.project.service;

import com.project.model.Plik;
import com.project.model.Projekt;
import com.project.repository.PlikiRepository;
import com.project.validation.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class PlikServiceImpl implements PlikService {
    @Value(value = "${file.upload-dir}")
    private String sciezka;
    @Autowired
    private PlikiRepository plikiRepository;


    //    public Plik storeFile(MultipartFile file, Projekt projekt) throws FileStorageException {
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//
//        try {
//            if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//            // Kopiowanie pliku do określonej ścieżki
//            Path targetLocation = Paths.get(sciezka + "/" + fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            // Generowanie URI do pobrania pliku
//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/downloadFile/")
//                    .path(fileName)
//                    .toUriString();
//
//            Plik fileModel = new Plik(fileName, fileDownloadUri, projekt);
//
//            return plikiRepository.save(fileModel);
//        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file ");
//        } catch (FileStorageException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public Plik storeFile(MultipartFile file, Projekt projekt) throws FileStorageException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Kopiowanie pliku do określonej ścieżki
            Path targetLocation = Paths.get(sciezka + "/" + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Generowanie URI do pobrania pliku
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            // Zapisywanie pełnej ścieżki do pliku w bazie danych
            String fullFilePath = targetLocation.toAbsolutePath().toString();

            Plik fileModel = new Plik(fileName, fileDownloadUri, fullFilePath, projekt);

            return plikiRepository.save(fileModel);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file ");
        } catch (FileStorageException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Plik> getPlikiByProjekt(Projekt projekt) {
        return plikiRepository.findByProjekt(projekt);
    }

    public void deleteFile(Integer plikId) {
        plikiRepository.deleteById(Long.valueOf(plikId));
    }

    @Override
    public String getFilePath(String fileName) throws FileNotFoundException {
        Plik file = plikiRepository.findByFileName(fileName);
        if (file != null) {
            return file.getFullFilePath();
        } else {
            throw new FileNotFoundException("File with name " + fileName + " not found");
        }
    }
}
