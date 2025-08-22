package com.example.view.console;

import com.example.model.Label;
import com.example.model.Post;
import com.example.model.Writer;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

/**
 * Console view implementation for Writer entities.
 */

public class WriterConsoleViewImpl extends ConsoleView<Writer> {

    private final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public WriterConsoleViewImpl(InputStream inputStream, PrintStream printStream) {
        super(inputStream, printStream);
    }

    @Override
    public void showOne(Writer entity) {
        getPrintStream().println("===========================");
        getPrintStream().println("Writer ID: " + entity.getId());
        getPrintStream().println("Writer first name: " + entity.getFirstName());
        getPrintStream().println("Writer last name: " + entity.getLastName());
        for (Post p : entity.getPosts()) {
            getPrintStream().println("===========================");
            getPrintStream().println("\tPost ID: " + p.getId());
            getPrintStream().println("\tPost content: " + p.getContent());
            getPrintStream().println("\tPost created at: " + df.format(p.getCreated()));
            getPrintStream().println("\tPost updated at: " + df.format(p.getUpdated()));
            for (Label l : p.getLabels()) {
                getPrintStream().println("\t\tLabel ID: " + l.getId());
                getPrintStream().println("\t\tLabel name: " + l.getName());
            }
        }
    }
}
