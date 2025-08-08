package com.example.model;

import java.util.List;

/**
 * POJO Post class.
 */

public class Post extends Entity {
    private Long id;
    private String title;
    private String content;
    private List<Label> labels;

    /**
     * Post constructor.
     * @param id
     * @param title
     * @param content
     * @param labels
     */

    public Post(Long id, String title, String content, List<Label> labels) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public List<Label> getLabels() {
        return labels;
    }
}
