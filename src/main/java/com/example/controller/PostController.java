package com.example.controller;

import com.example.model.Post;
import com.example.service.PostService;
import com.example.view.GenericView;

public abstract class PostController implements Controller {
    protected GenericView<Post> view;
    protected PostService service;
    protected ControllerClientStatus clientStatus;

    public PostController(GenericView<Post> view, PostService service) {
        this.service = service;
        this.view = view;
    }
}
