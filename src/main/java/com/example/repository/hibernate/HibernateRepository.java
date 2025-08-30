package com.example.repository.hibernate;

import com.example.repository.GenericRepository;
import com.example.repository.RepositoryException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TransactionRequiredException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 * Abstract Hibernate implementation of the GenericRepository interface.
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier
 */

public abstract class HibernateRepository<T, ID> implements GenericRepository<T, ID> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final Class<T> entityClass;

    public HibernateRepository(EntityManagerFactory entityManagerFactory, Class<T> entityClass) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityClass = entityClass;
    }

    protected EntityManager getNewEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public T getById(ID id) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            T entity = em.find(entityClass, id);
            return entity;
        } catch (IllegalArgumentException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> getAll() throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            em.getTransaction().begin();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);
            List<T> entities = em.createQuery(cq).getResultList();
            em.getTransaction().commit();
            return entities;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public T save(T entity) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public T update(T entity) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return entity;
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ID id) throws RepositoryException {
        try (EntityManager em = getNewEntityManager()) {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            } else {
                throw new RepositoryException(
                    entityClass.getName()
                    + " with id "
                    + id
                    + " not found",
                    new NullPointerException()
                );
            }
            em.getTransaction().commit();
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
