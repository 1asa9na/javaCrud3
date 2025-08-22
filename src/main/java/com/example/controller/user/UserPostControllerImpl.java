package com.example.controller.user;

import com.example.controller.ControllerClientStatus;
import com.example.controller.PostController;
import com.example.model.Post;
import com.example.service.PostService;
import com.example.service.ServiceException;
import com.example.view.GenericView;
import com.example.view.ViewException;
import java.util.List;

/**
 * Implementation of PostController with user permission.
 */

public class UserPostControllerImpl extends PostController {

    public UserPostControllerImpl(GenericView<Post> view, PostService service) {
        super(view, service);
        setClientStatus(ControllerClientStatus.USER);
    }

    @Override
    public void create() {
        try {
            Long writerId = getView().getInputID("Enter ID of the writer:");
            String content = getView().getInputString("Enter content of the post:");
            Post post = new Post();
            post.setContent(content);
            post.setWriterId(writerId);
            Post newPost = getService().save(post);
            getView().showMessage("Post created.");
            getView().showOne(newPost);
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void update() {
        try {
            Long id = getView().getInputID("Enter ID of the post.");
            String content = getView().getInputString("Enter content of the post.");
            Post post = new Post();
            post.setId(id);
            post.setContent(content);
            Post newPost = getService().save(post);
            getView().showMessage("Post updated.");
            getView().showOne(newPost);
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            Long id = getView().getInputID("Enter ID of the post.");
            getService().delete(id);
            getView().showMessage("Post " + id + " has been deleted successfully.");
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

    @Override
    public void read() {
        try {
            String input = getView().getInputString("Enter post ID or \"all\" to select all posts.");
            if ("all".equalsIgnoreCase(input)) {
                List<Post> posts = getService().getAll();
                getView().showMany(posts);
            } else {
                Long id = Long.parseLong(input);
                Post post = getService().getById(id);
                getView().showOne(post);
            }
        } catch (NumberFormatException e) {
            getView().showMessage("Error: wrong input.");
        } catch (ServiceException | ViewException e) {
            getView().showMessage(e.getMessage());
        }
    }

}
