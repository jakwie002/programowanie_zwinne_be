package com.project.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.project.model.ProjektDTO;
import com.project.model.Student;
import com.project.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.repository.ProjektRepository;
import com.project.repository.ZadanieRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjektServiceImpl implements ProjektService {

    private ProjektRepository projektRepository;
    private ZadanieRepository zadanieRepository;
    private StudentRepository studentRepository;

    @Autowired //adnotację można pomijać, jeżeli nie ma kilku wersji konstruktora
    public ProjektServiceImpl(ProjektRepository projektRepository, ZadanieRepository zadanieRepo, StudentRepository studentRepository) {
        this.projektRepository = projektRepository;
        this.zadanieRepository = zadanieRepo;
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Projekt> getProjekt(Integer projektId) {
        return projektRepository.findById(projektId);
    }

    @Override
    public Projekt setProjekt(ProjektDTO projekt) {
        Projekt projektCreate = new Projekt();
        projektCreate.setNazwa(projekt.getNazwa());
        projektCreate.setOpis(projekt.getOpis());

        // Pobieranie listy studentów na podstawie identyfikatorów
        Set<Student> studenci = new HashSet<>();
        for (Integer id : projekt.getStudentIds()) {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Nie można znaleźć studenta o id " + id));
            studenci.add(student);
        }

        projektCreate.setStudenci(studenci);
        return projektRepository.save(projektCreate);
    }
    @Override
    @Transactional
    public void deleteProjekt(Integer projektId) {
        for (Zadanie zadanie : zadanieRepository.findZadaniaProjektu(projektId)) {
            zadanieRepository.delete(zadanie);
        }
        projektRepository.deleteById(projektId);
    }

    @Override
    public Page<Projekt> getProjekty(Pageable pageable) {
        return projektRepository.findAll(pageable);
    }

    @Override
    public Page<Projekt> searchByNazwa(String nazwa, Pageable pageable) {
        return projektRepository.findByNazwa(nazwa, pageable);
    }

    @Override
    public Projekt updateProjekt(ProjektDTO projektDto, Integer projektId) {
        Projekt existingProjekt = projektRepository.findById(projektId)
                .orElseThrow(() -> new RuntimeException("Nie można znaleźć projektu o id " + projektId));

        // Aktualizacja pól istniejącego obiektu Projekt
        existingProjekt.setNazwa(projektDto.getNazwa());
        existingProjekt.setOpis(projektDto.getOpis());

        // Pobieranie listy studentów na podstawie identyfikatorów
        Set<Student> studenci = new HashSet<>();
        for (Integer id : projektDto.getStudentIds()) {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Nie można znaleźć studenta o id " + id));
            studenci.add(student);
        }

        existingProjekt.setStudenci(studenci);

        // Zapisanie i zwrócenie zaktualizowanego obiektu Projekt
        return projektRepository.save(existingProjekt);
    }
}