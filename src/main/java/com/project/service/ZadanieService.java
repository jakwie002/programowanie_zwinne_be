package com.project.service;

import java.util.Optional;

import com.project.model.ZadanieDTO;
import com.project.validation.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.model.Zadanie;

public interface ZadanieService {
    Optional<Zadanie> getZadanie(Integer zadanieId);

    Zadanie setZadanie(ZadanieDTO zadanie);
    Zadanie updateZadanie(Zadanie zadanie,Integer zadanieId) throws ResourceNotFoundException;

    void deleteZadanie(Integer zadanieId);

    Page<Zadanie> getZadania(Pageable pageable);

    Page<Zadanie> searchByNazwa(String nazwa, Pageable pageable);

    Page<Zadanie> searchById(Integer nazwa, Pageable pageable);

}
