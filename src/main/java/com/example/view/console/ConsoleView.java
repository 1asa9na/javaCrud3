package com.example.view.console;

import java.io.InputStream;
import java.io.PrintStream;

import com.example.view.GenericView;

public abstract class ConsoleView<T> implements GenericView<T> {
    protected PrintStream printStream;
    protected InputStream inputStream;

    public ConsoleView(InputStream inputStream, PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }
}
