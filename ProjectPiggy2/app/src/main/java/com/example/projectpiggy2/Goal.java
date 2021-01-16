package com.example.projectpiggy2;

public class Goal {
    String name;
    double amountSaved;
    double totalAmount;
    boolean isReached;

    public Goal(String name, double totalAmount) {
        this.name = name;
        this.amountSaved = 0;
        this.totalAmount = totalAmount;
        this.isReached = false;
    }

    public Goal(String name, double amountSaved, double totalAmount, boolean isReached) {
        this.name = name;
        this.amountSaved = amountSaved;
        this.totalAmount = totalAmount;
        this.isReached = isReached;
    }

    public String getName() {
        return this.name;
    }

    public double getAmountSaved() {
        return this.amountSaved;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public boolean getIsReached() {
        return this.isReached;
    }

    public boolean addSaving(double val) {
        this.amountSaved += val;
        if (this.amountSaved >= this.totalAmount) {
            this.isReached = true;
        }
        return this.isReached;
    }
}