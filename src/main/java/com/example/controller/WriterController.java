package com.example.controller;

import com.example.model.Writer;
import com.example.service.WriterService;
import com.example.view.GenericView;

public abstract class WriterController implements Controller {
    protected GenericView<Writer> view;
    protected WriterService service;
    protected ControllerClientStatus clientStatus;

    public WriterController(GenericView<Writer> view, WriterService service) {
        this.service = service;
        this.view = view;
    }
}
