package com.example.projectpiggy2;

public class Account {
    double balance;

    public Account() {
        this.balance = 0;
    }

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // any modifications to the balance should only occur in the User class
    public void addBalance(double balance) {
        this.balance += balance;
    }

    // any modifications to the balance should only occur in the User Class
    public void withdrawBalance(double balance) {
        this.balance -= balance;
    }

    public String toString() {
        return PriceFormatter.format(this.balance);
    }
}
