package com.example;

import com.example.controller.WriterController;
import com.example.model.Writer;
import com.example.repository.GsonPostRepositoryImpl;
import com.example.repository.GsonWriterRepositoryImpl;
import com.example.repository.WriterRepository;
import com.example.view.WriterView;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Writer functionality.
 */

public class WriterTest {

    @Test
    public void testPositiveWriterCreation() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writers1.json"));
        } catch (IOException e) {
        }
        WriterRepository writerRepository = new GsonWriterRepositoryImpl("src\\test\\resources\\writers1.json");
        WriterController writerController = new WriterController(
            writerRepository,
            new GsonPostRepositoryImpl(""),
            new WriterView(new ByteArrayInputStream("1\nJohn\nDoe\n0\n".getBytes()), System.out)
        );

        writerController.add();
        Writer writer = null;
        try {
            List<Writer> writers = writerRepository.getAll();
            writer = writers.get(writers.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("John", writer.getFirstName());
        assertEquals("Doe", writer.getLastName());
        assertEquals(new ArrayList<>(), writer.getPosts());
    }

    @Test
    public void testPositiveWriterDeletion() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writers2.json"));
        } catch (IOException e) {
        }
        WriterRepository writerRepository = new GsonWriterRepositoryImpl("src\\test\\resources\\writers2.json");
        int size = 0;
        int newSize = 0;
        try {
            writerRepository.save(new Writer(2L, "John", "Doe", new ArrayList<>()));
            size = writerRepository.getAll().size();
            WriterController writerController = new WriterController(
                writerRepository,
                new GsonPostRepositoryImpl(""),
                new WriterView(
                    new ByteArrayInputStream("2\n".getBytes()),
                    System.out
                )
            );
            writerController.delete();
            newSize = writerRepository.getAll().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(size - 1, newSize);
    }

    @Test
    public void testPositiveWriterUpdate() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writers3.json"));
        } catch (IOException e) {
        }
        WriterRepository writerRepository = new GsonWriterRepositoryImpl("src\\test\\resources\\writers3.json");
        String name = "";
        try {
            writerRepository.save(new Writer(1L, "John", "Doe", new ArrayList<>()));
            WriterController writerController = new WriterController(
                    writerRepository,
                    new GsonPostRepositoryImpl(""),
                    new WriterView(
                            new ByteArrayInputStream("1\nJane\nDoe\n0\n".getBytes()),
                            System.out));
            writerController.update();
            name = writerRepository.getById(1L).getFirstName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("Jane", name);
    }
}
