package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO Writer class.
 */

public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer() {
        this.posts = new ArrayList<Post>();
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
