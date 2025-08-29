package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PostStatus status;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Writer writer;

    @ManyToMany
    @JoinTable(
        name = "post_labels",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels = new ArrayList<>();

    @Column(name = "writer_id")
    private Long writerId;

    public Post() {
        // Default constructor
    }

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

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }
}
