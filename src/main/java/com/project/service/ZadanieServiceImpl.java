package com.project.service;

import java.util.Optional;

import com.project.model.ZadanieDTO;
import com.project.repository.ProjektRepository;
import com.project.validation.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.repository.ZadanieRepository;

@Service
public class ZadanieServiceImpl implements ZadanieService {

    private ZadanieRepository zadanieRepository;
    private ProjektRepository projektRepository;

    @Autowired
    public ZadanieServiceImpl(ZadanieRepository zadanieRepository, ProjektRepository projektRepository) {
        this.zadanieRepository = zadanieRepository;
        this.projektRepository = projektRepository;
    }

    @Override
    public Optional<Zadanie> getZadanie(Integer zadanieId) {
        return zadanieRepository.findById(zadanieId);
    }

    @Override
    public Zadanie setZadanie(ZadanieDTO zadanieDTO) {
        Zadanie zadanie = new Zadanie();
        zadanie.setNazwa(zadanieDTO.getNazwa());
        zadanie.setKolejnosc(Math.toIntExact(zadanieDTO.getKolejnosc()));
        zadanie.setOpis(zadanieDTO.getOpis());

        // Wyszukiwanie projektu po identyfikatorze
        if (zadanieDTO.getProjektId() != null) {
            Optional<Projekt> optionalProjekt = projektRepository.findById(Math.toIntExact(zadanieDTO.getProjektId()));
            optionalProjekt.ifPresent(zadanie::setProjekt);
        }

        return zadanieRepository.save(zadanie);
    }

    @Override
    public Zadanie updateZadanie(Zadanie zadanie,Integer zadanieId) throws ResourceNotFoundException {
        return zadanieRepository.findById(zadanieId)
                .map(z -> {
                    z.setNazwa(zadanie.getNazwa());
                    z.setOpis(zadanie.getOpis());
                    z.setKolejnosc(zadanie.getKolejnosc());
                    z.setDataCzasDodania(zadanie.getDataCzasDodania());
                    return zadanieRepository.save(z);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Zadanie", "id", zadanie.getZadanieId()));
    }

    @Override
    public void deleteZadanie(Integer zadanieId) {
        zadanieRepository.deleteById(zadanieId);
    }

    @Override
    public Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieRepository.findAll(pageable);
    }

    @Override
    public Page<Zadanie> searchByNazwa(String nazwa, Pageable pageable) {
        return zadanieRepository.findZadanieByNazwa(nazwa, pageable);
    }

    @Override
    public Page<Zadanie> searchById(Integer nazwa, Pageable pageable) {
        return zadanieRepository.findZadaniaProjektu(nazwa, pageable);
    }


}