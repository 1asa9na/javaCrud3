package com.example.view.console;

import com.example.view.GenericView;
import com.example.view.ViewException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Abstract console view for displaying entities.
 * @param <T> the type of the entity
 */

public abstract class ConsoleView<T> implements GenericView<T> {
    private PrintStream printStream;
    private Scanner sc;

    /**
     * Constructor for ConsoleView.
     * @param inputStream the input stream
     * @param printStream the print stream
     */

    public ConsoleView(InputStream inputStream, PrintStream printStream) {
        this.printStream = printStream;
        this.sc = new Scanner(inputStream);
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public Scanner getScanner() {
        return sc;
    }

    @Override
    public Long getInputID(String msg) throws ViewException {
        try {
            printStream.println(msg);
            return sc.nextLong();
        } catch (NoSuchElementException | IllegalStateException e) {
            throw new ViewException("Exception caught on view.getInputID: " + e.getMessage(), e);
        }
    }

    @Override
    public String getInputString(String msg) throws ViewException {
        try {
            printStream.println(msg);
            return sc.nextLine();
        } catch (NoSuchElementException | IllegalStateException e) {
            throw new ViewException("Exception caught on view.getInputString: " + e.getMessage(), e);
        }
    }

    @Override
    public void showMessage(String msg) {
        printStream.println(msg);
    }

    @Override
    public void showMany(List<T> entities) {
        for (T entity : entities) {
            showOne(entity);
        }
    }
}
