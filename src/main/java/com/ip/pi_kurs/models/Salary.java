package com.ip.pi_kurs.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Salary {
    private int id;
    private int workerId;
    private double salary;
    private Timestamp period;

    public String getPeriodString() {
        return periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
    }

    private String periodString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Timestamp getPeriod() {
        return period;
    }

    public void setPeriod(Timestamp period) {
        this.period = period;
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
