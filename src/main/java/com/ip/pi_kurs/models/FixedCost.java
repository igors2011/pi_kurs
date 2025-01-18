package com.ip.pi_kurs.models;

import java.sql.Timestamp;

public class FixedCost {
    private int id;
    private String name;
    private double cost;
    private Timestamp period;
    private String periodString;

    public String getPeriodString() {
        return periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
    }

    public Timestamp getPeriod() {
        return period;
    }

    public void setPeriod(Timestamp period) {
        this.period = period;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
