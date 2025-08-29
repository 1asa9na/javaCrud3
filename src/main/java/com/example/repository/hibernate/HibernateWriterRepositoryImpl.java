package com.example.repository.hibernate;

import com.example.model.Post;
import com.example.model.Writer;
import com.example.repository.RepositoryException;
import com.example.repository.WriterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TransactionRequiredException;
import java.util.List;

public class HibernateWriterRepositoryImpl extends HibernateRepository<Writer, Long> implements WriterRepository {

    public HibernateWriterRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Writer.class);
    }

    @Override
    public List<Post> getAllPostsByWriterId(Long id) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            em.getTransaction().begin();
            Writer w = em.find(getEntityClass(), id);
            em.getTransaction().commit();
            return w.getPosts();
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
