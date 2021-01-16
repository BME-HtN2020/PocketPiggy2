package com.example.projectpiggy2;

public class Chore {
    String title;
    String details;
    double amount;
    boolean isAccomplished;

    public Chore(String title, String details, double amount) {
        this.title = title;
        this.details = details;
        this.amount = amount;
        this.isAccomplished = false;
    }

    public Chore(String title, String details, double amount, boolean isAccomplished) {
        this.title = title;
        this.details = details;
        this.amount = amount;
        this.isAccomplished = isAccomplished;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDetails() {
        return this.details;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean getIsAccomplished() {
        return this.isAccomplished;
    }

    public void setAccomplished() {
        this.isAccomplished = true;
    }
}
