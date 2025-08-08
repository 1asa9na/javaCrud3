package com.example.repository;

import java.io.IOException;
import java.util.List;
/**
 * Generic Repository Interface.
 * @param <T> the type of entity
 * @param <ID> the type of entity identifier
 */

public interface GenericRepository<T, ID> {

    void save(T entity) throws IOException;

    T getById(ID id) throws IOException;

    List<T> getAll() throws IOException;

    void update(T entity, ID id) throws IOException;

    void delete(ID id) throws IOException;

    ID getNextId() throws IOException;
}
