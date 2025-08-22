package com.example.service;

import java.util.List;

/**
 * Generic service interface for CRUD operations.
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier
 */

public interface GenericService<T, ID> {

    T getById(ID id) throws ServiceException;

    List<T> getAll() throws ServiceException;

    T save(T entity) throws ServiceException;

    T update(T entity) throws ServiceException;

    void delete(ID id) throws ServiceException;
}
