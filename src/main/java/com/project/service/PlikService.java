package com.project.service;

import com.project.model.Plik;
import com.project.model.Projekt;
import com.project.validation.FileStorageException;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;


public interface PlikService {

    Plik storeFile(MultipartFile file, Projekt projekt) throws FileStorageException;

    List<Plik> getPlikiByProjekt(Projekt projekt);

    void deleteFile(Integer plikId);

    String getFilePath(String fileName) throws FileNotFoundException;
}
