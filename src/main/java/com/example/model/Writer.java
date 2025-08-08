package com.example.model;

import java.util.List;

/**
 * POJO Writer class.
 */

public class Writer extends Entity {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    /**
     * Writer constructor.
     * @param id
     * @param firstName
     * @param lastName
     * @param posts
     */

    public Writer(Long id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public List<Post> getPosts() {
        return posts;
    }
}
