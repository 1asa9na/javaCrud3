package com.example.service;

import com.example.model.Writer;
import com.example.repository.WriterRepository;

public abstract class WriterService implements GenericService<Writer, Long> {
    protected WriterRepository repository;

    public WriterService(WriterRepository repository) {
        this.repository = repository;
    }
}
