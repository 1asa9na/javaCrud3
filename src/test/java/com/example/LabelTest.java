package com.example;

import com.example.controller.LabelController;
import com.example.model.Label;
import com.example.repository.GsonLabelRepositoryImpl;
import com.example.repository.LabelRepository;
import com.example.view.LabelView;
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
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Label functionality.
 */

public class LabelTest {

    @Test
    public void testPositiveLabelCreation() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\labels1.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\labels1.json");
        LabelController lc = new LabelController(
                lr,
                new LabelView(new ByteArrayInputStream("1\nAAA\n".getBytes()), System.out));

        lc.add();
        Label label = null;
        try {
            List<Label> labels = lr.getAll();
            label = labels.get(labels.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("AAA", label.getName());
    }

    @Test
    public void testPositiveLabelDeletion() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\labels2.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\labels2.json");
        int size = 0;
        int newSize = 0;
        try {
            lr.save(new Label(2L, "AAA"));
            size = lr.getAll().size();
            LabelController lc = new LabelController(
                    lr,
                    new LabelView(
                            new ByteArrayInputStream("2\n".getBytes()),
                            System.out));
            lc.delete();
            newSize = lr.getAll().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(size - 1, newSize);
    }

    @Test
    public void testPositiveLabelUpdate() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\labels3.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\labels3.json");
        String name = "";
        try {
            lr.save(new Label(1L, "AAA"));
            LabelController lc = new LabelController(
                    lr,
                    new LabelView(
                            new ByteArrayInputStream("1\nBBB\n".getBytes()),
                            System.out));
            lc.update();
            name = lr.getById(1L).getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("BBB", name);
    }

    @Test
    public void testNegativeLabelDeleteNotExist() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\labels4.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\labels4.json");
        LabelController lc = new LabelController(
                lr,
                new LabelView(
                        new ByteArrayInputStream("2\n".getBytes()),
                        System.out));
        try {
            lr.save(new Label(1L, "AAA"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lc.delete();
        int newSize = 0;
        try {
            newSize = lr.getAll().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(1, newSize);
    }

    @Test
    public void testNegativeLabelDataIsNull() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String utf8 = StandardCharsets.UTF_8.name();
        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\labels6.json");
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            LabelController lc = new LabelController(
                lr,
                new LabelView(
                        Mockito.mock(InputStream.class),
                        ps
                )
            );
            lc.getAll();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertEquals("[]\r\n", baos.toString());
    }

    @Test
    public void testPositiveGetLabelById() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\labels7.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String utf8 = StandardCharsets.UTF_8.name();
        Label lbl = new Label(1L, "AAA");
        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\labels7.json");
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            LabelController lc = new LabelController(
                    lr,
                    new LabelView(
                            new ByteArrayInputStream("1\n".getBytes()),
                            ps));
            lr.save(lbl);
            lc.getOne();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<Label>() {
        }.getType();
        Label newLbl;
        Gson gson = new Gson();
        newLbl = gson.fromJson(
                JsonParser.parseString("{" + baos.toString().split("\\{")[1]).getAsJsonObject(), listType);
        assertEquals(lbl.getName(), newLbl.getName());
    }

    @Test
    public void testNegativeLabelControllerAddException() {
        LabelRepository mockLr = Mockito.mock(LabelRepository.class);
        LabelView mockLv = Mockito.mock(LabelView.class);
        try {
            Mockito.when(mockLr.getNextId()).thenThrow(new IOException());
            Mockito.doReturn(1).when(mockLv).getNumber(Mockito.anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelController lc = new LabelController(
                mockLr,
                mockLv);
        lc.add();
        Mockito.verify(mockLv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeLabelControllerUpdateException() {
        LabelRepository mockLr = Mockito.mock(LabelRepository.class);
        LabelView mockLv = Mockito.mock(LabelView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockLr).update(Mockito.any(Label.class), Mockito.anyLong());
            Mockito.doReturn(1L).when(mockLv).getID();
            Mockito.doReturn(Mockito.mock(Label.class)).when(mockLv).getObject(Mockito.anyLong());
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelController lc = new LabelController(
                mockLr,
                mockLv);
        lc.update();
        Mockito.verify(mockLv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeLabelControllerDeleteException() {
        LabelRepository mockLr = Mockito.mock(LabelRepository.class);
        LabelView mockLv = Mockito.mock(LabelView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockLr).delete(Mockito.anyLong());
            Mockito.doReturn(1L).when(mockLv).getID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelController lc = new LabelController(
                mockLr,
                mockLv);
        lc.delete();
        Mockito.verify(mockLv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeLabelControllerGetAllException() {
        LabelRepository mockLr = Mockito.mock(LabelRepository.class);
        LabelView mockLv = Mockito.mock(LabelView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockLr).getAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelController lc = new LabelController(
                mockLr,
                mockLv);
        lc.getAll();
        Mockito.verify(mockLv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeLabelControllerGetOneException() {
        LabelRepository mockLr = Mockito.mock(LabelRepository.class);
        LabelView mockLv = Mockito.mock(LabelView.class);
        try {
            Mockito.doThrow(new IOException()).when(mockLr).getById(Mockito.anyLong());
            Mockito.doReturn(1L).when(mockLv).getID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LabelController lc = new LabelController(
                mockLr,
                mockLv);
        lc.getOne();
        Mockito.verify(mockLv, Mockito.times(1)).showMessage(null);
    }

    @Test
    public void testNegativeLabelGetNumberViewNumberFormatException() {
        LabelView lv = new LabelView(
                new ByteArrayInputStream("A\n".getBytes()),
                System.out);
        int result = lv.getNumber("");
        assertEquals(0, result);
    }

    @Test
    public void testNegativeLabelGetNumberViewIDFormatException() {
        LabelView lv = new LabelView(
                new ByteArrayInputStream("A\n".getBytes()),
                System.out);
        Long result = lv.getID();
        assertEquals(null, result);
    }

    @Test
    public void testNegativeLabelControllerGetOneWasNotFound() {
        try {
            Files.deleteIfExists(Paths.get("src\\test\\resources\\labels8.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LabelRepository lr = new GsonLabelRepositoryImpl("src\\test\\resources\\labels8.json");
        LabelView mockLv = Mockito.mock(LabelView.class);
        Mockito.doReturn(2L).when(mockLv).getID();

        try {
            lr.save(new Label(1L, "AAA"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LabelController lc = new LabelController(
            lr,
            mockLv
        );
        lc.getOne();

        Mockito.verify(mockLv, Mockito.times(1)).showMessage("Label not found.");
    }
}
