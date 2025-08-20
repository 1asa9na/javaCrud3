package com.example.service;

import java.util.List;

import com.example.controller.ControllerClientStatus;
import com.example.model.Post;
import com.example.repository.PostRepository;

public abstract class PostService implements GenericService<Post, Long> {
    protected PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public abstract Post getById(Long id, ControllerClientStatus clientStatus) throws Exception;
    public abstract List<Post> getAll(ControllerClientStatus clientStatus) throws Exception;
    public abstract Post update(Post entity, ControllerClientStatus clientStatus) throws Exception;
    public abstract void delete(Long id, ControllerClientStatus clientStatus) throws Exception;
}
