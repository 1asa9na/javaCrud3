package com.example.repository.hibernate;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import jakarta.persistence.EntityManagerFactory;

/**
 * Hibernate implementation of the LabelRepository interface.
 */

public class HibernateLabelRepositoryImpl extends HibernateRepository<Label, Long> implements LabelRepository {

    public HibernateLabelRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, Label.class);
    }
}
