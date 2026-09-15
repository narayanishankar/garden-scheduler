package com.example.gardenapp;

public class Journal {
    private int id;
    private String title;

    private String text;
    private String date;

    public Journal(int id, String title, String text, String date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
