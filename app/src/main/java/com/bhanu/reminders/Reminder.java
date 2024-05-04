package com.bhanu.reminders;
public class Reminder {
    private int id;
    private String title;
    private long dateTime;

    public Reminder(int id, String title, long dateTime) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
