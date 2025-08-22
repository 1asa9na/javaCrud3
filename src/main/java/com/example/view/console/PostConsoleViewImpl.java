package com.example.view.console;

import com.example.model.Label;
import com.example.model.Post;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

/**
 * Console view implementation for Post entities.
 */

public class PostConsoleViewImpl extends ConsoleView<Post> {

    private final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public PostConsoleViewImpl(InputStream inputStream, PrintStream printStream) {
        super(inputStream, printStream);
    }

    @Override
    public void showOne(Post entity) {
        getPrintStream().println("===========================");
        getPrintStream().println("Post ID: " + entity.getId());
        getPrintStream().println("Post content: " + entity.getContent());
        getPrintStream().println("Post created at: " + df.format(entity.getCreated()));
        getPrintStream().println("Post updated at: " + df.format(entity.getUpdated()));
        for (Label l : entity.getLabels()) {
            getPrintStream().println("\tLabel ID: " + l.getId());
            getPrintStream().println("\tLabel name: " + l.getName());
        }
    }
}
