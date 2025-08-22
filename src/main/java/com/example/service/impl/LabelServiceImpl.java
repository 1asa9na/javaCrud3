package com.example.service.impl;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import com.example.repository.RepositoryException;
import com.example.service.LabelService;
import com.example.service.ServiceException;
import java.util.List;

/**
 * Implementation of the LabelService.
 */

public class LabelServiceImpl extends LabelService {

    public LabelServiceImpl(LabelRepository repository) {
        super(repository);
    }

    @Override
    public Label getById(Long id) throws ServiceException {
        try {
            return getRepository().getById(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getById", e);
        }
    }

    @Override
    public List<Label> getAll() throws ServiceException {
        try {
            return getRepository().getAll();
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getAll", e);
        }
    }

    @Override
    public Label save(Label entity) throws ServiceException {
        try {
            return getRepository().save(entity);
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.save", e);
        }
    }

    @Override
    public Label update(Label entity) throws ServiceException {
        try {
            return getRepository().update(entity);
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.update", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            getRepository().deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.delete", e);
        }
    }
}
