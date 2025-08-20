package com.example.service.impl;

import java.util.Date;
import java.util.List;

import com.example.controller.ControllerClientStatus;
import com.example.model.Label;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.repository.PostRepository;
import com.example.service.PostService;

public class PostServiceImpl extends PostService {

    public PostServiceImpl(PostRepository repository) {
        super(repository);
    }

    @Override
    public Post getById(Long id, ControllerClientStatus clientStatus) throws Exception {
        Post p = repository.getById(id);
        if(p.getStatus() == PostStatus.DELETED || p.getStatus() == PostStatus.UNDER_REVIEW && clientStatus == ControllerClientStatus.USER) {
            return null;
        }
        p.setLabels(repository.getAllLabelsByPostId(id));
        return p;
    }

    @Override
    public Post getById(Long id) throws Exception {
        Post p = repository.getById(id);
        p.setLabels(repository.getAllLabelsByPostId(id));
        return p;
    }

    @Override
    public List<Post> getAll(ControllerClientStatus clientStatus) throws Exception {
        List<Post> posts = repository.getAll();
        posts.removeIf(p -> p.getStatus().equals(PostStatus.DELETED) || p.getStatus() == PostStatus.UNDER_REVIEW
                && clientStatus == ControllerClientStatus.USER);
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i); 
            List<Label> labels = repository.getAllLabelsByPostId(p.getId());
            p.setLabels(labels);
            posts.set(i, p);
        }
        return posts;
    }

    @Override
    public List<Post> getAll() throws Exception {
        List<Post> posts = repository.getAll();
        for (int i = 0; i < posts.size(); i++) {
            Post p = posts.get(i);
            List<Label> labels = repository.getAllLabelsByPostId(p.getId());
            p.setLabels(labels);
            posts.set(i, p);
        }
        return posts;
    }

    @Override
    public Post save(Post entity) throws Exception {
        entity.setCreated(new Date());
        entity.setUpdated(entity.getCreated());
        Post p = repository.save(entity);
        return p;
    }

    @Override
    public Post update(Post entity, ControllerClientStatus clientStatus) throws Exception {
        entity.setUpdated(new Date());
        Post p = repository.getById(entity.getId());
        if(p.getStatus() == PostStatus.DELETED || p.getStatus() == PostStatus.UNDER_REVIEW && clientStatus == ControllerClientStatus.USER) {
            return null;
        }
        p = repository.update(entity);
        p.setLabels(repository.getAllLabelsByPostId(p.getId()));
        return p;
    }

    @Override
    public Post update(Post entity) throws Exception {
        entity.setUpdated(new Date());
        Post p = repository.update(entity);
        p.setLabels(repository.getAllLabelsByPostId(p.getId()));
        return p;
    }

    @Override
    public void delete(Long id, ControllerClientStatus clientStatus) throws Exception {
        Post p = repository.getById(id);
        if (p.getStatus().equals(PostStatus.UNDER_REVIEW) && clientStatus.equals(ControllerClientStatus.USER)) {
        } else {
            repository.deleteById(id);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        repository.deleteById(id);
    }

}
