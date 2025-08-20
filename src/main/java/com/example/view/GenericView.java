package com.example.view;

import java.util.List;

public interface GenericView<T> {
    void showMessage(String msg);
    void showOne(T entity);
    void showMany(List<T> entity);

    String getInputString(String msg);
    Long getInputID(String msg);
}
