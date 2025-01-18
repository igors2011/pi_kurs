package com.ip.pi_kurs.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void convertPeriodStringToPeriod() {
        String periodString = getPeriodString();
        LocalDateTime localDateTime = LocalDateTime.parse(periodString);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        setPeriod(timestamp);
    }

    public void convertPeriodToPeriodString() {
        Timestamp period = getPeriod();
        LocalDateTime localDateTime = period.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String periodString = localDateTime.format(formatter);
        setPeriodString(periodString);
    }

}
