package com.bits.trackr.Model;

public class TaskModel {

    private int id, status;
    public String title,content;
    public TaskModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() { return content;}

    public void setContent(String content) { this.content=content; }
}
