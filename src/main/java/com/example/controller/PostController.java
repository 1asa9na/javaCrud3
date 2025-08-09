package com.example.controller;

import com.example.model.Post;
import com.example.repository.LabelRepository;
import com.example.repository.PostRepository;
import com.example.view.PostView;
import java.io.IOException;

/**
 * Post Controller class.
 */
public class PostController implements GenericController<Post, Long> {
    private final PostRepository postRepository;
    private final LabelRepository labelRepository;
    private final PostView postView;

    /**
     * Post Controller constructor.
     * @param postRepository
     * @param labelRepository
     * @param postView
     */

    public PostController(PostRepository postRepository, LabelRepository labelRepository, PostView postView) {
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
        this.postView = postView;
    }

    private Post create(Long id) {
        return postView.getObject(id);
    }

    @Override
    public void add() {
        int numberOfPosts = postView.getNumber("Enter number of posts to add: ");
        for (int i = 0; i < numberOfPosts; i++) {
            try {
                Long id = postRepository.getNextId();
                Post post = this.create(id);
                int numberOfLabels = postView.getNumber("Enter number of labels for the post: ");
                for (int j = 0; j < numberOfLabels; j++) {
                    Long labelId = postView.getID();
                    post.addLabel(labelRepository.getById(labelId));
                }
                postRepository.save(post);
            } catch (IOException e) {
                postView.showMessage(e.getMessage());
            }
        }
    }

    @Override
    public void update() {
        try {
            Long id = postView.getID();
            Post post = this.create(id);
            postRepository.update(post, id);
            postView.showMessage("Post updated successfully.");
            postView.display(post);
        } catch (IOException e) {
            postView.showMessage(e.getMessage());
        }
    }

    @Override
    public void delete() {
        try {
            Long id = postView.getID();
            postRepository.delete(id);
            postView.showMessage("Post deleted successfully.");
        } catch (IOException e) {
            postView.showMessage(e.getMessage());
        }
    }

    @Override
    public void getAll() {
        try {
            postView.displayList(postRepository.getAll());
        } catch (IOException e) {
            postView.showMessage(e.getMessage());
        }
    }

    @Override
    public void getOne() {
        try {
            Long id = postView.getID();
            Post post = postRepository.getById(id);
            if (post != null) {
                postView.display(post);
            } else {
                postView.showMessage("Post not found.");
            }
        } catch (IOException e) {
            postView.showMessage(e.getMessage());
        }
    }
}
