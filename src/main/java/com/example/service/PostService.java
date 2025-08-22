package com.example.service;

import com.example.controller.ControllerClientStatus;
import com.example.model.Post;
import com.example.repository.PostRepository;
import java.util.List;

/**
 * Abstract service class for managing posts.
 */

public abstract class PostService implements GenericService<Post, Long> {
    private PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public PostRepository getRepository() {
        return repository;
    }

    public abstract Post getById(Long id, ControllerClientStatus clientStatus) throws ServiceException;

    public abstract List<Post> getAll(ControllerClientStatus clientStatus) throws ServiceException;

    public abstract Post update(Post entity, ControllerClientStatus clientStatus) throws ServiceException;

    public abstract void delete(Long id, ControllerClientStatus clientStatus) throws ServiceException;
}
