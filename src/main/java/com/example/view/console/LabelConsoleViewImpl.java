package com.example.view.console;

import com.example.model.Label;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Console view implementation for Label entities.
 */

public class LabelConsoleViewImpl extends ConsoleView<Label> {

    public LabelConsoleViewImpl(InputStream inputStream, PrintStream printStream) {
        super(inputStream, printStream);
    }

    @Override
    public void showOne(Label entity) {
        getPrintStream().println("===========================");
        getPrintStream().println("Label ID: " + entity.getId());
        getPrintStream().println("Label name: " + entity.getName());
    }
}
