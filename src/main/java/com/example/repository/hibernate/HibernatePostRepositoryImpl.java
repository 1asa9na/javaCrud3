package com.example.repository.hibernate;

import com.example.model.Label;
import com.example.model.Post;
import com.example.repository.PostRepository;
import com.example.repository.RepositoryException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TransactionRequiredException;
import java.util.List;

public class HibernatePostRepositoryImpl extends HibernateRepository<Post, Long> implements PostRepository {

    public HibernatePostRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Post.class);
    }

    @Override
    public List<Label> getAllLabelsByPostId(Long id) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            Post p = em.find(getEntityClass(), id);
            return p.getLabels();
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteLabelFromPostById(Long postId, Long labelId) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            em.getTransaction().begin();
            Post post = em.find(Post.class, postId);
            Label label = em.find(Label.class, labelId);
            if (post == null) {
                throw new RepositoryException("Post with id " + postId + " not found", new NullPointerException());
            }
            if (label == null) {
                throw new RepositoryException("Label with id " + labelId + " not found", new NullPointerException());
            }
            post.getLabels().remove(label);
            em.merge(post);
            em.getTransaction().commit();
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void addLabelToPostById(Long postId, Long labelId) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            em.getTransaction().begin();
            Post post = em.find(Post.class, postId);
            Label label = em.find(Label.class, labelId);
            if (post == null) {
                throw new RepositoryException("Post with id " + postId + " not found", new NullPointerException());
            }
            if (label == null) {
                throw new RepositoryException("Label with id " + labelId + " not found", new NullPointerException());
            }
            post.getLabels().add(label);
            em.merge(post);
            em.getTransaction().commit();
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
