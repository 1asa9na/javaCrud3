package com.example.view;

import com.example.model.Post;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Post View class.
 */

public class PostView implements GenericView<Post, Long> {
    private GsonBuilder gsonPretty = new GsonBuilder().setPrettyPrinting();
    private final PrintStream output;
    private final Scanner scanner;

    public PostView(InputStream input, PrintStream output) {
        this.scanner = new Scanner(input);
        this.output = output;
    }

    @Override
    public void display(Post item) {
        output.println(gsonPretty.create().toJson(item));
    }

    @Override
    public int getNumber(String prompt) {
        try {
            output.print(prompt);
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            output.println("Error reading input: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void displayList(List<Post> items) {
        output.println(gsonPretty.create().toJson(items));
    }

    @Override
    public Post getObject(Long id) {
        output.print("Enter post title: ");
        String title = scanner.nextLine();
        output.print("Enter post content: ");
        String content = scanner.nextLine();
        return new Post(id, title, content, new ArrayList<>());
    }

    @Override
    public String getString(String prompt) {
        output.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void showError(String message) {
        output.println("Error: " + message);
    }

    @Override
    public void showMessage(String message) {
        output.println("Message: " + message);
    }

    @Override
    public Long getID() {
        try {
            output.print("Enter post ID: ");
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            output.println("Error reading input: " + e.getMessage());
        }
        return null;
    }
}
