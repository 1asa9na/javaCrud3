package com.example.repository;

import java.util.List;
/**
 * Generic Repository Interface.
 * @param <T> the type of entity
 * @param <ID> the type of entity identifier
 */

public interface GenericRepository<T, ID> {

    T getById(ID id) throws Exception;

    List<T> getAll() throws Exception;

    T save(T entity) throws Exception;

    T update(T entity) throws Exception;

    void deleteById(ID id) throws Exception;
}
