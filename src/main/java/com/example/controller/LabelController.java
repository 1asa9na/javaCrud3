package com.example.controller;

import com.example.model.Label;
import com.example.service.LabelService;
import com.example.view.GenericView;

/**
 * Abstract class for Label controllers.
 */

public abstract class LabelController implements Controller {
    private GenericView<Label> view;
    private LabelService service;

    public LabelController(GenericView<Label> view, LabelService service) {
        this.service = service;
        this.view = view;
    }

    public GenericView<Label> getView() {
        return view;
    }

    public LabelService getService() {
        return service;
    }
}
