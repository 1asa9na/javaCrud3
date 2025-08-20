package com.example.service;

import com.example.model.Label;
import com.example.repository.LabelRepository;

public abstract class LabelService implements GenericService<Label, Long> {
    protected LabelRepository repository;

    public LabelService(LabelRepository repository) {
        this.repository = repository;
    }
}
