package jp.co.topgate.teru.web;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setContent(String comment) {
        this.content = comment;
    }
    public String getContent() {
        return this.content;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

}