package com.example.projectpiggy2;

import java.util.*;

public class User {
    String id;
    String email;
    String pin;
    String name;
    Account account;
    List<Chore> chores;
    Goal goal;

    public User(String email, String pin, String name) {
        this.email = email;
        this.pin = pin;
        this.name = name;
        this.account = new Account();
        this.chores = new ArrayList<Chore>();
        this.goal = null;
    }

    public User(String email, String pin, String name, Account account, List<Chore> chores, Goal goal) {
        this.email = email;
        this.pin = pin;
        this.name = name;
        this.account = account;
        this.chores = chores;
        this.goal = goal;
    }

    public String getId() {
        return this.id;
    }

    public String getEmail() { return this.email; }

    public String getPin() { return this.pin; }

    public String getName() {
        return this.name;
    }

    public Account getAccount() {
        return this.account;
    }

    public List<Chore> getChores() {
        return this.chores;
    }

    public Goal getGoal() {
        return this.goal;
    }

    public void setGoal(String name, double totalAmount) {
        this.goal = new Goal(name, totalAmount);
    }

    public void assignChore(String title, String details, double amount) {
        Chore chore = new Chore(title, details, amount);
        this.chores.add(chore);
    }

    public void giveAllowance(double val) {
        this.account.addBalance(val);
    }

    public void addSavings(double val) {
        this.goal.addSaving(val);
        this.account.withdrawBalance(val);
    }

    public void doChore(String choreName) {
        for (int i = 0; i < this.chores.size(); i++)
        {
            if (chores.get(i).getTitle().equals(choreName)) chores.get(i).setAccomplished();
        }
    }

    public boolean verify(String pin) {
        return pin.equals(this.pin);
    }
}
