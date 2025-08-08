package com.example.repository;

import com.example.model.Label;
import com.example.model.Status;
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
 * Gson implementation of the LabelRepository interface.
 */

public class GsonLabelRepositoryImpl implements LabelRepository {
    private final String filePath;
    private GsonBuilder gsonBuilder = new GsonBuilder();

    public GsonLabelRepositoryImpl(String filePath) {
        this.filePath = filePath;
    }

    private List<Label> readData() throws IOException {
        Type listType = new TypeToken<List<Label>>() {
        }.getType();
        List<Label> labels;

        try (FileReader reader = new FileReader(filePath)) {
            labels = gsonBuilder.create().fromJson(reader, listType);
            if (labels == null) {
                labels = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            labels = new ArrayList<>();
        }

        return labels;
    }

    private void writeData(List<Label> labels) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            String json = gsonBuilder.create().toJson(labels);
            outputStream.write(json.getBytes());
        }
    }

    @Override
    public void save(Label entity) throws IOException {
        List<Label> labels = readData();

        // Добавление объекта
        labels.add(entity);

        writeData(labels);
    }

    @Override
    public List<Label> getAll() throws IOException {
        List<Label> labels = readData();

        return labels.stream().filter(label -> label.getStatus() != Status.DELETED).collect(Collectors.toList());
    }

    @Override
    public Label getById(Long id) throws IOException {
        List<Label> labels = readData();

        for (Label label : labels) {
            if (label.getId().equals(id) && label.getStatus() != Status.DELETED) {
                return label;
            }
        }

        return null;
    }

    @Override
    public void update(Label entity, Long id) throws IOException {
        List<Label> labels = readData();

        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).getId().equals(id) && labels.get(i).getStatus() != Status.DELETED) {
                labels.set(i, entity);
                break;
            }
        }

        writeData(labels);
    }

    @Override
    public void delete(Long id) throws IOException {
        List<Label> labels = readData();

        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).getId().equals(id)) {
                labels.get(i).setStatus(Status.DELETED);
                break;
            }
        }

        writeData(labels);
    }

    @Override
    public Long getNextId() throws IOException {
        List<Label> labels = readData();

        Long maxId = labels.stream()
                .map(Label::getId)
                .max(Long::compareTo)
                .orElse(0L);

        return maxId + 1;
    }
}
