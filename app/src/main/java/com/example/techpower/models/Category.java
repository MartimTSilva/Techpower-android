package com.example.techpower.models;

public class Category {
    private int id;
    private String name;
    private int parentId;

    public Category(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parentId;
    }

    public void setParent_id(int parent_id) {
        this.parentId = parent_id;
    }
}
