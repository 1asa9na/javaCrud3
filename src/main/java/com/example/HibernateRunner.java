package com.example;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import com.example.repository.hibernate.HibernateLabelRepositoryImpl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateRunner {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CRUD");
        LabelRepository labelRepository = new HibernateLabelRepositoryImpl(entityManagerFactory);

        // Create a new label
        Label newLabel = new Label();
        newLabel.setName("New Label");
        System.out.println(labelRepository.save(newLabel).getId());

        entityManagerFactory.close();
    }

}
