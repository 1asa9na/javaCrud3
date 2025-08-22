package com.example.controller;

import com.example.model.Post;
import com.example.service.PostService;
import com.example.view.GenericView;

/**
 * Abstract class for Post controllers.
 */

public abstract class PostController implements Controller {
    private GenericView<Post> view;
    private PostService service;
    private ControllerClientStatus clientStatus;

    public PostController(GenericView<Post> view, PostService service) {
        this.service = service;
        this.view = view;
    }

    public GenericView<Post> getView() {
        return view;
    }

    public PostService getService() {
        return service;
    }

    public ControllerClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ControllerClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }
}
