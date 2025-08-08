package com.example.controller;

/**
 * Generic Controller Interface.
 * @param <T> the type of entity
 * @param <ID> the type of entity identifier
 */

interface GenericController<T, ID> {

    void add();

    void update();

    void delete();

    void getAll();

    void getOne();
}
