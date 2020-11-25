package com.bits.trackr.Model;

public class TaskModel {

    private int id, status;
    public String title;
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

    public void setTask(String title) {
        this.title = title;
    }
}
