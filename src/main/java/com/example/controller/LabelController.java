package com.example.controller;

import com.example.model.Label;
import com.example.repository.LabelRepository;
import com.example.view.LabelView;
import java.io.IOException;

/**
 * Controller for managing labels.
 */
public class LabelController implements GenericController<Label, Long> {
    private final LabelRepository labelRepository;
    private final LabelView labelView;

    /**
     * Label Controller constructor.
     * @param labelRepository
     * @param labelView
     */
    public LabelController(LabelRepository labelRepository, LabelView labelView) {
        this.labelRepository = labelRepository;
        this.labelView = labelView;
    }

    private Label create(Long id) {
        return labelView.getObject(id);
    }

    @Override
    public void add() {
        int numberOfLabels = labelView.getNumber("Enter number of labels to add: ");
        for (int i = 0; i < numberOfLabels; i++) {
            try {
                Long id = labelRepository.getNextId();
                Label label = this.create(id);
                labelRepository.save(label);
            } catch (IOException e) {
                labelView.showMessage(e.getMessage());
            }
        }
    }

    @Override
    public void update() {
        try {
            Long id = labelView.getID();
            Label label = this.create(id);
            labelRepository.update(label, id);
            labelView.showMessage("Label updated successfully.");
            labelView.display(label);
        } catch (IOException e) {
            labelView.showMessage(e.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            Long id = labelView.getID();
            labelRepository.delete(id);
            labelView.showMessage("Label deleted successfully.");
        } catch (IOException e) {
            labelView.showMessage(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        try {
            labelView.displayList(labelRepository.getAll());
        } catch (IOException e) {
            labelView.showMessage(e.getMessage());
        }
    }

    @Override
    public void getOne() {
        try {
            Long id = labelView.getID();
            Label label = labelRepository.getById(id);
            if (label != null) {
                labelView.display(label);
            } else {
                labelView.showMessage("Label not found.");
            }
        } catch (IOException e) {
            labelView.showMessage(e.getMessage());
        }
    }
}
