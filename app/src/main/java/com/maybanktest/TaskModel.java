package com.maybanktest;

public class TaskModel {
    private String id;
    private String title;
    private String start_date;
    private String end_date;
    private boolean isCompleted;

    TaskModel(String id, String title, String start_date, String end_date, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.isCompleted = isCompleted;
    }

    String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    boolean getIsCompleted() {
        return isCompleted;
    }

    void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
