package com.codvision.figurinestore.module.bean;


public class CommoditySubmit {
    private int id;

    private int choice;

    private int time;

    public CommoditySubmit(int id, int choice, int time) {
        this.id = id;
        this.choice = choice;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
