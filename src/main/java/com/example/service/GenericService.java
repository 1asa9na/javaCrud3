package com.example.service;

import java.util.List;

public interface GenericService<T, ID> {
    T getById(ID id) throws Exception;
    List<T> getAll() throws Exception;
    T save(T entity) throws Exception;
    T update(T entity) throws Exception;
    void delete(ID id) throws Exception;
}
