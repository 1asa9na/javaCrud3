package com.example.view;

import com.example.model.Label;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Label View class.
 */

public class LabelView implements GenericView<Label, Long> {
    private GsonBuilder gsonPretty = new GsonBuilder().setPrettyPrinting();
    private final PrintStream output;
    private final Scanner scanner;

    public LabelView(InputStream input, PrintStream output) {
        this.scanner = new Scanner(input);
        this.output = output;
    }

    @Override
    public void display(Label item) {
        output.println(gsonPretty.create().toJson(item));
    }

    @Override
    public int getNumber(String prompt) {
        output.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public void displayList(List<Label> items) {
        output.println(gsonPretty.create().toJson(items));
    }

    @Override
    public Label getObject(Long id) {
        output.print("Enter label name: ");
        String name = scanner.nextLine();
        return new Label(id, name);
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
            output.print("Enter label ID: ");
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            output.println("Error reading input: " + e.getMessage());
        }
        return null;
    }
}
