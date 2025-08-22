package com.example.service;

import com.example.model.Label;
import com.example.repository.LabelRepository;

/**
 * Abstract service class for managing labels.
 */

public abstract class LabelService implements GenericService<Label, Long> {
    private LabelRepository repository;

    public LabelService(LabelRepository repository) {
        this.repository = repository;
    }

    public LabelRepository getRepository() {
        return repository;
    }
}
