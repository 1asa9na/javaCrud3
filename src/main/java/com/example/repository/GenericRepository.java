package com.example.repository;

import java.util.List;
/**
 * Generic Repository Interface.
 * @param <T> the type of entity
 * @param <ID> the type of entity identifier
 */

public interface GenericRepository<T, ID> {

    T getById(ID id) throws RepositoryException;

    List<T> getAll() throws RepositoryException;

    T save(T entity) throws RepositoryException;

    T update(T entity) throws RepositoryException;

    void deleteById(ID id) throws RepositoryException;
}
