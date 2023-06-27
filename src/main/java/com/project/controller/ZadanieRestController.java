package com.project.controller;

import com.project.model.ZadanieDTO;
import com.project.validation.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.model.Zadanie;
import com.project.service.ZadanieService;


@RestController
@RequestMapping("/api")
public class ZadanieRestController {

    private ZadanieService zadanieService;

    @Autowired
    public ZadanieRestController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
    }

    @GetMapping("/zadania/{zadanieId}")
    ResponseEntity<Zadanie> getZadanie(@PathVariable Integer zadanieId) {// pobieranie zadania
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId));
    }

    @PostMapping(path = "/zadania")
//tworzenie zadania
    ResponseEntity<Void> createZadanie(@Valid @RequestBody ZadanieDTO zadanie) {
        zadanieService.setZadanie(zadanie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/zadania/{zadanieId}")
    public Zadanie updateZadanie(@Valid @RequestBody Zadanie zadanie, @PathVariable Integer zadanieId) throws ResourceNotFoundException {
        return zadanieService.updateZadanie(zadanie, zadanieId);
    }

    @DeleteMapping("/zadania/{zadanieId}")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer zadanieId) {
        return zadanieService.getZadanie(zadanieId).map(p -> {
            zadanieService.deleteZadanie(zadanieId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping(value = "/zadania")
    Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieService.getZadania(pageable);
    }


    @GetMapping(value = "/zadania", params = "nazwa")
    Page<Zadanie> getZadaniaByNazwa(@RequestParam String nazwa, Pageable pageable) {
        return zadanieService.searchByNazwa(nazwa, pageable);
    }

    @GetMapping(value = "/projekt/{id}/zadania")
    Page<Zadanie> getZadaniaByProjektId(@PathVariable Integer id, Pageable pageable) {
        return zadanieService.searchById(id, pageable);
    }
}