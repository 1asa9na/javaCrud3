package com.example.model;

/**
 * Entity abstract class.
 * Represents entities of the "database"
 */
public abstract class Entity {
    private Status status;

    public Entity() {
        this.status = Status.ACTIVE;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
