package com.example.simcardapp;

public class SIMModel {
    private int id;
    private String simNumber;
    private String simType;
    private int kkId;

    public SIMModel(int id, String simNumber, String simType, int kkId) {
        this.id = id;
        this.simNumber = simNumber;
        this.simType = simType;
        this.kkId = kkId;
    }

    public int getId() {
        return id;
    }

    public String getSimNumber() {
        return simNumber;
    }
    public String getSimType() {
        return simType;
    }

    public int getKkId() {
        return kkId;
    }
}

