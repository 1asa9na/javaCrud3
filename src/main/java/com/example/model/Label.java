package com.example.model;

/**
 * POJO Label class.
 */

public class Label extends Entity {
    private Long id;
    private String name;

    /**
     * Label constructor.
     * @param id
     * @param name
     */
    public Label(Long id, String name) {
        this.id = id;
        this.name = name; // Default status
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
