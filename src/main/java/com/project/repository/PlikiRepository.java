package com.project.repository;

import com.project.model.Plik;
import com.project.model.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlikiRepository extends JpaRepository<Plik, Long> {
    List<Plik> findByProjekt(Projekt projekt);

    Plik findByFileName(String fileName);
}
