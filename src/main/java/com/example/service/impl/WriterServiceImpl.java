package com.example.service.impl;

import com.example.controller.ControllerClientStatus;
import com.example.model.Label;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.model.Writer;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.repository.WriterRepository;
import com.example.service.ServiceException;
import com.example.service.WriterService;
import java.util.List;

/**
 * Implementation of the WriterService.
 */

public class WriterServiceImpl extends WriterService {

    public WriterServiceImpl(WriterRepository writerRepository, PostRepository postRepository) {
        super(writerRepository, postRepository);
    }

    @Override
    public Writer getById(Long id) throws ServiceException {
        try {
            Writer w = getWriterRepository().getById(id);
            List<Post> posts = getWriterRepository().getAllPostsByWriterId(id);
            for (Post p : posts) {
                List<Label> labels = getPostRepository().getAllLabelsByPostId(p.getId());
                p.setLabels(labels);
            }
            w.setPosts(posts);
            return w;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getById", e);
        }
    }

    @Override
    public Writer getById(Long id, ControllerClientStatus clientStatus) throws ServiceException {
        try {
            Writer w = getWriterRepository().getById(id);
            List<Post> posts = getWriterRepository().getAllPostsByWriterId(id);
            posts.removeIf(p -> p.getStatus().equals(PostStatus.DELETED) || p.getStatus() == PostStatus.UNDER_REVIEW
                    && clientStatus == ControllerClientStatus.USER);
            for (Post p : posts) {
                List<Label> labels = getPostRepository().getAllLabelsByPostId(p.getId());
                p.setLabels(labels);
            }
            w.setPosts(posts);
            return w;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getById", e);
        }
    }

    @Override
    public List<Writer> getAll() throws ServiceException {
        try {
            List<Writer> writers = getWriterRepository().getAll();
            for (Writer w : writers) {
                List<Post> posts = getWriterRepository().getAllPostsByWriterId(w.getId());
                for (Post p : posts) {
                    List<Label> labels = getPostRepository().getAllLabelsByPostId(p.getId());
                    p.setLabels(labels);
                }
                w.setPosts(posts);
            }
            return writers;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getAll", e);
        }
    }

    @Override
    public List<Writer> getAll(ControllerClientStatus clientStatus) throws ServiceException {
        try {
            List<Writer> writers = getWriterRepository().getAll();
            for (Writer w : writers) {
                List<Post> posts = getWriterRepository().getAllPostsByWriterId(w.getId());
                posts.removeIf(p -> p.getStatus().equals(PostStatus.DELETED) || p.getStatus() == PostStatus.UNDER_REVIEW
                        && clientStatus == ControllerClientStatus.USER);
                for (Post p : posts) {
                    List<Label> labels = getPostRepository().getAllLabelsByPostId(p.getId());
                    p.setLabels(labels);
                }
                w.setPosts(posts);
            }
            return writers;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getAll", e);
        }
    }

    @Override
    public Writer save(Writer entity) throws ServiceException {
        try {
            Writer w = getWriterRepository().save(entity);
            return w;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.save", e);
        }
    }

    @Override
    public Writer update(Writer entity) throws ServiceException {
        try {
            Writer w = getWriterRepository().update(entity);
            return w;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.update", e);
        }
    }

    @Override
    public Writer update(Writer entity, ControllerClientStatus clientStatus) throws ServiceException {
        try {
            Writer w = getWriterRepository().update(entity);
            List<Post> posts = getWriterRepository().getAllPostsByWriterId(w.getId());
            posts.removeIf(p -> p.getStatus().equals(PostStatus.DELETED) || p.getStatus() == PostStatus.UNDER_REVIEW
                    && clientStatus == ControllerClientStatus.USER);
            for (Post p : posts) {
                List<Label> labels = getPostRepository().getAllLabelsByPostId(p.getId());
                p.setLabels(labels);
            }
            w.setPosts(posts);
            return w;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.update", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            getWriterRepository().deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.delete", e);
        }
    }
}
