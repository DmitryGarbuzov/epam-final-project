package com.garbuzov.diary.entity;

import java.io.Serializable;

public abstract class Entity implements Cloneable, Serializable {
    private long id;

    public Entity() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
