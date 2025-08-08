package com.example.controller;

import com.example.model.Writer;
import com.example.repository.PostRepository;
import com.example.repository.WriterRepository;
import com.example.view.WriterView;
import java.io.IOException;

/**
 * Writer Controller class.
 */
public class WriterController implements GenericController<Writer, Long> {
    private final WriterRepository writerRepository;
    private final PostRepository postRepository;
    private final WriterView writerView;

    /**
     * Writer Controller constructor.
     * @param writerRepository
     * @param postRepository
     * @param writerView
     */
    public WriterController(WriterRepository writerRepository, PostRepository postRepository, WriterView writerView) {
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
        this.writerView = writerView;
    }

    private Writer create(Long id) {
        return writerView.getObject(id);
    }

    @Override
    public void add() {
        int numberOfWriters = writerView.getNumber("Enter number of writers to add: ");
        for (int i = 0; i < numberOfWriters; i++) {
            try {
                Long id = writerRepository.getNextId();
                Writer writer = this.create(id);
                int numberOfPosts = writerView.getNumber("Enter number of posts for the writer: ");
                for (int j = 0; j < numberOfPosts; j++) {
                    Long postId = writerView.getID();
                    writer.addPost(postRepository.getById(postId));
                }
                writerRepository.save(writer);
            } catch (IOException e) {
                writerView.showError(e.getMessage());
            }
        }
    }

    @Override
    public void update() {
        try {
            Long id = writerView.getID();
            Writer writer = this.create(id);
            writerRepository.update(writer, id);
            writerView.showMessage("Writer updated successfully.");
            writerView.display(writer);
        } catch (IOException e) {
            writerView.showError(e.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            Long id = writerView.getID();
            writerRepository.delete(id);
            writerView.showMessage("Writer deleted successfully.");
        } catch (IOException e) {
            writerView.showError(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        try {
            writerView.displayList(writerRepository.getAll());
        } catch (IOException e) {
            writerView.showError(e.getMessage());
        }
    }

    @Override
    public void getOne() {
        try {
            Long id = writerView.getID();
            Writer writer = writerRepository.getById(id);
            if (writer != null) {
                writerView.display(writer);
            } else {
                writerView.showError("Writer not found.");
            }
        } catch (IOException e) {
            writerView.showError(e.getMessage());
        }
    }
}
