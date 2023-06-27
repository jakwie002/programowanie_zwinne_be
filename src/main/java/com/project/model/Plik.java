package com.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "file")
public class Plik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    public String getFullFilePath() {
        return fullFilePath;
    }

    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }

    @Column(nullable = false)
    private String fileDownloadUri;
    private String fullFilePath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projekt_id", nullable = false)
    @JsonBackReference
    private Projekt projekt;

    public Plik(String fileName, String fileDownloadUri, Projekt projekt) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.projekt = projekt;
    }

    public Plik() {

    }

    public Plik(String fileName, String fileDownloadUri, String fullFilePath, Projekt projekt) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fullFilePath = fullFilePath;
        this.projekt = projekt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
}
