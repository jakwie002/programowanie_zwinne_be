package com.project.service;

import java.util.Optional;

import com.project.model.ProjektDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.model.Projekt;

public interface ProjektService {
    Optional<Projekt> getProjekt(Integer projektId);

    Projekt setProjekt(ProjektDTO projekt);

    void deleteProjekt(Integer projektId);

    Page<Projekt> getProjekty(Pageable pageable);

    Page<Projekt> searchByNazwa(String nazwa, Pageable pageable);

     Projekt updateProjekt(ProjektDTO projektDto, Integer projektId);
}
