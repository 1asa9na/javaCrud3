package com.example.repository;

import com.example.model.Label;
import com.example.model.Post;
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
 * Gson implementation of the PostRepository interface.
 */

public class GsonPostRepositoryImpl implements PostRepository {
    private final String filePath;
    private GsonBuilder gsonBuilder = new GsonBuilder();

    public GsonPostRepositoryImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Post entity) throws IOException {
        List<Post> posts = readData();

        // Добавление объекта
        posts.add(entity);

        // Сохранение списка обратно в файл
        writeData(posts);
    }

    @Override
    public List<Post> getAll() throws IOException {
        List<Post> posts = readData();

        return posts.stream().filter(post -> post.getStatus() != Status.DELETED).collect(Collectors.toList());
    }

    @Override
    public Post getById(Long id) throws IOException {
        List<Post> posts = readData();

        for (Post post : posts) {
            if (post.getId().equals(id) && post.getStatus() != Status.DELETED) {
                return post;
            }
        }

        return null;
    }

    @Override
    public void update(Post entity, Long id) throws IOException {
        List<Post> posts = readData();

        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(id) && posts.get(i).getStatus() != Status.DELETED) {
                posts.set(i, entity);
                break;
            }
        }

        writeData(posts);
    }

    private List<Post> readData() throws IOException {
        Type listType = new TypeToken<List<Post>>() {
        }.getType();
        List<Post> posts;

        try (FileReader reader = new FileReader(filePath)) {
            posts = gsonBuilder.create().fromJson(reader, listType);
            if (posts == null) {
                posts = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            posts = new ArrayList<>();
        }

        return posts;
    }

    private void writeData(List<Post> posts) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            String json = gsonBuilder.create().toJson(posts);
            outputStream.write(json.getBytes());
        }
    }

    /**
     * Adds labels to post.
     * @param labels
     * @param id
     * @throws IOException
     */
    public void addLabels(List<Label> labels, Long id) throws IOException {
        List<Post> posts = readData();

        for (Post post : posts) {
            if (post.getId().equals(id) && post.getStatus() != Status.DELETED) {
                for (Label label : labels) {
                    post.addLabel(label);
                }
                break;
            }
        }

        writeData(posts);
    }

    @Override
    public void delete(Long id) throws IOException {
        List<Post> posts = readData();

        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(id)) {
                posts.get(i).setStatus(Status.DELETED);
                break;
            }
        }

        writeData(posts);
    }

    @Override
    public Long getNextId() throws IOException {
        List<Post> posts = readData();

        Long maxId = posts.stream()
                .map(Post::getId)
                .max(Long::compareTo)
                .orElse(0L);

        return maxId + 1;
    }
}
