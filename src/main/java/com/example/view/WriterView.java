package com.example.view;

import com.example.model.Writer;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Writer View class.
 */

public class WriterView implements GenericView<Writer, Long> {
    private GsonBuilder gsonPretty = new GsonBuilder().setPrettyPrinting();
    private final PrintStream output;
    private final Scanner scanner;

    public WriterView(InputStream input, PrintStream output) {
        this.output = output;
        this.scanner = new Scanner(input);
    }

    @Override
    public void display(Writer item) {
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
    public void displayList(List<Writer> items) {
        output.println(gsonPretty.create().toJson(items));
    }

    @Override
    public Writer getObject(Long id) {
        output.print("Enter writer first name: ");
        String firstName = scanner.nextLine();
        output.print("Enter writer last name: ");
        String lastName = scanner.nextLine();
        return new Writer(id, firstName, lastName, new ArrayList<>());
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
            output.print("Enter writer ID: ");
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            output.println("Error reading input: " + e.getMessage());
        }
        return null;
    }
}
