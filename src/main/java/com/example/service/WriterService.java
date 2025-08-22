package com.example.service;

import com.example.controller.ControllerClientStatus;
import com.example.model.Writer;
import com.example.repository.PostRepository;
import com.example.repository.WriterRepository;
import java.util.List;

/**
 * Abstract service class for managing writers.
 */

public abstract class WriterService implements GenericService<Writer, Long> {
    private WriterRepository writerRepository;
    private PostRepository postRepository;

    /**
     * Constructor for WriterService.
     * @param writerRepository the writer repository
     * @param postRepository the post repository
     */

    public WriterService(WriterRepository writerRepository, PostRepository postRepository) {
        this.writerRepository = writerRepository;
        this.postRepository = postRepository;
    }

    public WriterRepository getWriterRepository() {
        return writerRepository;
    }

    public PostRepository getPostRepository() {
        return postRepository;
    }

    public abstract Writer getById(Long id, ControllerClientStatus clientStatus) throws ServiceException;

    public abstract List<Writer> getAll(ControllerClientStatus clientStatus) throws ServiceException;

    public abstract Writer update(Writer entity, ControllerClientStatus clientStatus) throws ServiceException;
}
