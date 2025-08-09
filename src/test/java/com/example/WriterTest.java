package com.example;

import com.example.controller.WriterController;
import com.example.model.Post;
import com.example.model.Writer;
import com.example.repository.GsonPostRepositoryImpl;
import com.example.repository.GsonWriterRepositoryImpl;
import com.example.repository.PostRepository;
import com.example.repository.WriterRepository;
import com.example.view.WriterView;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
            e.printStackTrace();
        }
        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers1.json");
        WriterController wc = new WriterController(
            wr,
                Mockito.mock(PostRepository.class),
            new WriterView(new ByteArrayInputStream("1\nJohn\nDoe\n0\n".getBytes()), System.out)
        );

        wc.add();
        Writer writer = null;
        try {
            List<Writer> writers = wr.getAll();
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
            e.printStackTrace();
        }
        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers2.json");
        int size = 0;
        int newSize = 0;
        try {
            wr.save(new Writer(2L, "John", "Doe", new ArrayList<>()));
            size = wr.getAll().size();
            WriterController wc = new WriterController(
                wr,
                Mockito.mock(PostRepository.class),
                new WriterView(
                    new ByteArrayInputStream("2\n".getBytes()),
                    System.out
                )
            );
            wc.delete();
            newSize = wr.getAll().size();
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
            e.printStackTrace();
        }
        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers3.json");
        String name = "";
        try {
            wr.save(new Writer(1L, "John", "Doe", new ArrayList<>()));
            WriterController wc = new WriterController(
                    wr,
                    Mockito.mock(PostRepository.class),
                    new WriterView(
                            new ByteArrayInputStream("1\nJane\nDoe\n0\n".getBytes()),
                            System.out));
            wc.update();
            name = wr.getById(1L).getFirstName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("Jane", name);
    }

    @Test
    public void testNegativeWriterDeleteNotExist() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writers4.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers4.json");
        WriterController wc = new WriterController(
            wr,
            Mockito.mock(PostRepository.class),
            new WriterView(
                new ByteArrayInputStream("2\n".getBytes()),
                System.out
            )
        );
        try {
            wr.save(new Writer(1L, "John", "Doe", new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        wc.delete();
        int newSize = 0;
        try {
            newSize = wr.getAll().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(1, newSize);
    }

    @Test
    public void testPositiveAddPostsToWriter() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writers5.json"));
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writersposts5.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers5.json");
        PostRepository pr = new GsonPostRepositoryImpl("src\\test\\resources\\writersposts5.json");

        try {
            pr.save(new Post(1L, "ABC", "abc", new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        WriterController wc = new WriterController(wr, pr, new WriterView(
            new ByteArrayInputStream("1\nJane\nDoe\n1\n1\n".getBytes()),
            System.out
        ));
        wc.add();

        String title = "";
        try {
            title = wr.getById(1L).getPosts().get(0).getTitle();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("ABC", title);
    }

    @Test
    public void testNegativeWriterDataIsNull() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String utf8 = StandardCharsets.UTF_8.name();
        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers6.json");
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            WriterController wc = new WriterController(
                wr,
                Mockito.mock(PostRepository.class),
                new WriterView(
                    Mockito.mock(InputStream.class),
                    ps
                )
            );
            wc.getAll();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<List<Writer>>() {
        }.getType();
        List<Writer> writers;
        writers = new Gson().fromJson(baos.toString(), listType);
        if (writers == null) {
            writers = new ArrayList<>();
        }
        assertEquals(0, writers.size());
    }

    @Test
    public void testPositiveGetWriterById() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writers7.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String utf8 = StandardCharsets.UTF_8.name();
        Writer wrtr = new Writer(1L, "Jane", "Doe", new ArrayList<>());
        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers7.json");
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            WriterController wc = new WriterController(
                wr,
                Mockito.mock(PostRepository.class),
                new WriterView(
                    new ByteArrayInputStream("1\n".getBytes()),
                    ps
                )
            );
            wr.save(wrtr);
            wc.getOne();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<Writer>() {
        }.getType();
        Writer newWrtr;
        Gson gson = new Gson();
        newWrtr = gson.fromJson(
            JsonParser.parseString("{" + baos.toString().split("\\{")[1]).getAsJsonObject(), listType);
        assertEquals(wrtr.getFirstName(), newWrtr.getFirstName());
    }

    @Test
    public void testNegativeWriterControllerAddException() {
        WriterRepository mockWr = Mockito.mock(WriterRepository.class);
        WriterView mockWv = Mockito.mock(WriterView.class);
        try {
            Mockito.when(mockWr.getNextId()).thenThrow(new IOException());
            Mockito.doReturn(1).when(mockWv).getNumber(Mockito.anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        WriterController wc = new WriterController(
            mockWr,
            Mockito.mock(PostRepository.class),
            mockWv
        );
        wc.add();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeWriterControllerUpdateException() {
        WriterRepository mockWr = Mockito.mock(WriterRepository.class);
        WriterView mockWv = Mockito.mock(WriterView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).update(Mockito.any(Writer.class), Mockito.anyLong());
            Mockito.doReturn(1L).when(mockWv).getID();
            Mockito.doReturn(Mockito.mock(Writer.class)).when(mockWv).getObject(Mockito.anyLong());
        } catch (IOException e) {
            e.printStackTrace();
        }
        WriterController wc = new WriterController(
            mockWr,
            Mockito.mock(PostRepository.class),
            mockWv);
        wc.update();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeWriterControllerDeleteException() {
        WriterRepository mockWr = Mockito.mock(WriterRepository.class);
        WriterView mockWv = Mockito.mock(WriterView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).delete(Mockito.anyLong());
            Mockito.doReturn(1L).when(mockWv).getID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WriterController wc = new WriterController(
            mockWr,
            Mockito.mock(PostRepository.class),
            mockWv);
        wc.delete();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeWriterControllerGetAllException() {
        WriterRepository mockWr = Mockito.mock(WriterRepository.class);
        WriterView mockWv = Mockito.mock(WriterView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WriterController wc = new WriterController(
                mockWr,
                Mockito.mock(PostRepository.class),
                mockWv);
        wc.getAll();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeWriterControllerGetOneException() {
        WriterRepository mockWr = Mockito.mock(WriterRepository.class);
        WriterView mockWv = Mockito.mock(WriterView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockWr).getById(Mockito.anyLong());
            Mockito.doReturn(1L).when(mockWv).getID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WriterController wc = new WriterController(
                mockWr,
                Mockito.mock(PostRepository.class),
                mockWv);
        wc.getOne();
        Mockito.verify(mockWv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeWriterGetNumberViewNumberFormatException() {
        WriterView wv = new WriterView(
            new ByteArrayInputStream("A\n".getBytes()),
            System.out
        );
        int result = wv.getNumber("");
        assertEquals(0, result);
    }

    @Test
    public void testNegativeWriterGetNumberViewIDFormatException() {
        WriterView wv = new WriterView(
                new ByteArrayInputStream("A\n".getBytes()),
                System.out);
        Long result = wv.getID();
        assertEquals(null, result);
    }

    @Test
    public void testNegativeWriterControllerGetOneWasNotFound() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\writers8.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        WriterRepository wr = new GsonWriterRepositoryImpl("src\\test\\resources\\writers8.json");
        WriterView mockWv = Mockito.mock(WriterView.class);
        Mockito.doReturn(2L).when(mockWv).getID();

        try {
            wr.save(new Writer(1L, "Jane", "Doe", new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        WriterController wc = new WriterController(
            wr,
            Mockito.mock(PostRepository.class),
            mockWv
        );
        wc.getOne();

        Mockito.verify(mockWv, Mockito.times(1)).showMessage("Writer not found.");
    }
}
