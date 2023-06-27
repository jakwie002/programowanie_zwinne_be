package com.project.validation;

import org.springframework.data.crossstore.ChangeSetPersister;

public class ResourceNotFoundException extends ChangeSetPersister.NotFoundException {
    public ResourceNotFoundException(String zadanie, String id, Integer zadanieId) {
    }
}
