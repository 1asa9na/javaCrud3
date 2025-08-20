package com.example.service.impl;

import java.util.List;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import com.example.service.LabelService;

public class LabelServiceImpl extends LabelService {

    public LabelServiceImpl(LabelRepository repository) {
        super(repository);
    }

    @Override
    public Label getById(Long id) throws Exception {
        return repository.getById(id);
    }

    @Override
    public List<Label> getAll() throws Exception {
        return repository.getAll();
    }

    @Override
    public Label save(Label entity) throws Exception {
        return repository.save(entity); 
    }

    @Override
    public Label update(Label entity) throws Exception {
        return repository.update(entity);
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }
    
}
