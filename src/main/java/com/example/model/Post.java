package com.example.model;

import java.util.Date;
import java.util.List;

/**
 * POJO Post class.
 */

public class Post{
    private Long id;
    private String content;
    private Date created;
    private Date updated;
    private PostStatus status;
    private List<Label> labels;

    public Post() {
        
    }

    /**
     * Post constructor.
     * @param id
     * @param content
     * @param labels
     * @param created
     * @param updated
     * @param status
     */

    public Post(Long id, String content, Date created, Date updated, PostStatus status, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.labels = labels;
        this.created = created;
        this.updated = updated;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }
}
