package com.example.service.impl;

import com.example.controller.ControllerClientStatus;
import com.example.model.Post;
import com.example.model.PostStatus;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import com.example.service.PostService;
import com.example.service.ServiceException;
import java.util.List;

/**
 * Implementation of the PostService.
 */

public class PostServiceImpl extends PostService {

    public PostServiceImpl(PostRepository repository) {
        super(repository);
    }

    private void addLabelsForPost(Post p) {
        p.setLabels(getRepository().getAllLabelsByPostId(p.getId()));
    }

    private boolean isPostVisibleForClient(Post p, ControllerClientStatus clientStatus) {
        return !(p.getStatus() == PostStatus.DELETED || p.getStatus() == PostStatus.UNDER_REVIEW
            && clientStatus == ControllerClientStatus.USER);
    }

    @Override
    public Post getById(Long id, ControllerClientStatus clientStatus) throws ServiceException {
        try {
            Post p = getRepository().getById(id);
            if (!isPostVisibleForClient(p, clientStatus)) {
                return null;
            }
            addLabelsForPost(p);
            return p;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getById", e);
        }
    }

    @Override
    public Post getById(Long id) throws ServiceException {
        try {
            Post p = getRepository().getById(id);
            addLabelsForPost(p);
            return p;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getById", e);
        }
    }

    @Override
    public List<Post> getAll(ControllerClientStatus clientStatus) throws ServiceException {
        try {
            List<Post> posts = getRepository().getAll();
            posts.removeIf(p -> !isPostVisibleForClient(p, clientStatus));
            for (Post p : posts) {
                addLabelsForPost(p);
            }
            return posts;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getAll", e);
        }
    }

    @Override
    public List<Post> getAll() throws ServiceException {
        try {
            List<Post> posts = getRepository().getAll();
            for (Post p : posts) {
                addLabelsForPost(p);
            }
            return posts;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.getAll", e);
        }
    }

    @Override
    public Post save(Post entity) throws ServiceException {
        try {
            Post p = getRepository().save(entity);
            return p;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.save", e);
        }
    }

    @Override
    public Post update(Post entity, ControllerClientStatus clientStatus) throws ServiceException {
        try {
            Post p = getRepository().getById(entity.getId());
            if (!isPostVisibleForClient(p, clientStatus)) {
                return null;
            }
            p = getRepository().update(entity);
            addLabelsForPost(p);
            return p;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.update", e);
        }
    }

    @Override
    public Post update(Post entity) throws ServiceException {
        try {
            Post p = getRepository().update(entity);
            addLabelsForPost(p);
            return p;
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.update", e);
        }
    }

    @Override
    public void delete(Long id, ControllerClientStatus clientStatus) throws ServiceException {
        try {
            Post p = getRepository().getById(id);
            if (isPostVisibleForClient(p, clientStatus)) {
                getRepository().deleteById(id);
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.delete", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            getRepository().deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Exception on service.delete", e);
        }
    }

}
