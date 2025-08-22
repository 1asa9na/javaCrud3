package com.example.view;

import java.util.List;

/**
 * Generic interface for views.
 *
 * @param <T> the type of the entity
 */

public interface GenericView<T> {

    void showMessage(String msg);

    void showOne(T entity);

    void showMany(List<T> entities);

    String getInputString(String msg) throws ViewException;

    Long getInputID(String msg) throws ViewException;
}
