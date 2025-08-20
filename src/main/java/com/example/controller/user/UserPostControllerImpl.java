package com.example.controller.user;

import java.util.List;
import com.example.controller.ControllerClientStatus;
import com.example.controller.PostController;
import com.example.model.Post;
import com.example.service.PostService;
import com.example.view.GenericView;

public class UserPostControllerImpl extends PostController {

    public UserPostControllerImpl(GenericView<Post> view, PostService service) {
        super(view, service);
        this.clientStatus = ControllerClientStatus.USER;
    }

    @Override
    public void create() {
        try {
            Long writerId = view.getInputID("Enter ID of the writer:");
            String content = view.getInputString("Enter content of the post:");
            Post post = new Post();
            post.setContent(content);
            post.setWriterId(writerId);
            Post newPost = service.save(post);
            view.showMessage("Post created.");
            view.showOne(newPost);
        } catch (Exception e) {
            view.showMessage("Error occured during CREATE.");
        }
    }

    @Override
    public void update() {
        try {
            Long id = view.getInputID("Enter ID of the post.");
            String content = view.getInputString("Enter content of the post.");
            Post post = new Post();
            post.setId(id);
            post.setContent(content);
            Post newPost = service.save(post);
            view.showMessage("Post updated.");
            view.showOne(newPost);
        } catch (Exception e) {
            view.showMessage("Error occured during UPDATE.");
        }
    }

    @Override
    public void delete() {
        try {
            Long id = view.getInputID("Enter ID of the post.");
            service.delete(id);
            view.showMessage("Post " + id + " has been deleted successfully.");
        } catch (Exception e) {
            view.showMessage("Error occured during DELETE.");
        }
    }

    @Override
    public void read() {
        try {
            String input = view.getInputString("Enter post ID or \"all\" to select all posts.");
            if (input.equalsIgnoreCase("all")) {
                List<Post> posts = service.getAll();
                view.showMany(posts);
            } else {
                Long id = Long.parseLong(input);
                Post post = service.getById(id);
                view.showOne(post);
            }
        } catch (NumberFormatException e) {
            view.showMessage("Error: wrong input.");
        } catch (Exception e) {
            view.showMessage("Error occured on READ.");
        }
    }

}
