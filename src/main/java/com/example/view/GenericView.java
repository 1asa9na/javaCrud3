package com.example.view;

import java.util.List;

/**
 * Generic View interface.
 * @param <T>  the type of entity
 * @param <ID> the type of entity identifier
 */
public interface GenericView<T, ID> {

    void display(T item);

    void displayList(List<T> items);

    T getObject(ID id);

    ID getID();

    int getNumber(String prompt);

    String getString(String prompt);

    void showError(String message);

    void showMessage(String message);
}
