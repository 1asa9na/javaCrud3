package com.example.repository;

import com.example.model.Status;
import com.example.model.Writer;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gson implementation of the WriterRepository interface.
 */

public class GsonWriterRepositoryImpl implements WriterRepository {
    private final String filePath;
    private GsonBuilder gsonBuilder = new GsonBuilder();

    public GsonWriterRepositoryImpl(String filePath) {
        this.filePath = filePath;
    }

    private List<Writer> readData() throws IOException {
        Type listType = new TypeToken<List<Writer>>() {
        }.getType();
        List<Writer> writers;

        try (FileReader reader = new FileReader(filePath)) {
            writers = gsonBuilder.create().fromJson(reader, listType);
            if (writers == null) {
                writers = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            writers = new ArrayList<>();
        }

        return writers;
    }

    private void writeData(List<Writer> writers) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            String json = gsonBuilder.create().toJson(writers);
            outputStream.write(json.getBytes());
        }
    }

    @Override
    public void save(Writer entity) throws IOException {
        List<Writer> writers = readData();

        writers.add(entity);

        writeData(writers);
    }

    @Override
    public List<Writer> getAll() throws IOException {
        List<Writer> writers = readData();

        return writers.stream().filter(writer -> writer.getStatus() != Status.DELETED).collect(Collectors.toList());
    }

    @Override
    public Writer getById(Long id) throws IOException {
        List<Writer> writers = readData();

        for (Writer writer : writers) {
            if (writer.getId().equals(id) && writer.getStatus() != Status.DELETED) {
                return writer;
            }
        }

        return null;
    }

    @Override
    public void update(Writer entity, Long id) throws IOException {
        List<Writer> writers = readData();

        for (int i = 0; i < writers.size(); i++) {
            if (writers.get(i).getId().equals(id) && writers.get(i).getStatus() != Status.DELETED) {
                writers.set(i, new Writer(id, entity.getFirstName(), entity.getLastName(), entity.getPosts()));
                break;
            }
        }

        writeData(writers);
    }

    @Override
    public void delete(Long id) throws IOException {
        List<Writer> writers = readData();

        for (int i = 0; i < writers.size(); i++) {
            if (writers.get(i).getId().equals(id)) {
                writers.get(i).setStatus(Status.DELETED);
                break;
            }
        }

        writeData(writers);
    }

    @Override
    public Long getNextId() throws IOException {
        List<Writer> writers = readData();

        Long maxId = writers.stream()
                .map(Writer::getId)
                .max(Long::compareTo)
                .orElse(0L);

        return maxId + 1;
    }
}
