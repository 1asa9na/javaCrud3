package com.example.controller;

import com.example.model.Label;
import com.example.service.LabelService;
import com.example.view.GenericView;

public abstract class LabelController implements Controller {
    protected GenericView<Label> view;
    protected LabelService service;

    public LabelController(GenericView<Label> view, LabelService service) {
        this.service = service;
        this.view = view;
    }
}
