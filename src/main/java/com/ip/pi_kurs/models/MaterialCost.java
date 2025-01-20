package com.ip.pi_kurs.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MaterialCost {
    private int id;
    private int materialId;
    private double cost;
    private Timestamp period;
    private String periodString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Timestamp getPeriod() {
        return period;
    }

    public void setPeriod(Timestamp period) {
        this.period = period;
    }

    public String getPeriodString() {
        return periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
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
