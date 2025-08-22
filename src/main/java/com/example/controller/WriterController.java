package com.example.controller;

import com.example.model.Writer;
import com.example.service.WriterService;
import com.example.view.GenericView;

/**
 * Abstract controller for managing writers.
 */

public abstract class WriterController implements Controller {
    private GenericView<Writer> view;
    private WriterService service;
    private ControllerClientStatus clientStatus;

    public WriterController(GenericView<Writer> view, WriterService service) {
        this.service = service;
        this.view = view;
    }

    public GenericView<Writer> getView() {
        return view;
    }

    public WriterService getService() {
        return service;
    }

    public ControllerClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ControllerClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }
}
