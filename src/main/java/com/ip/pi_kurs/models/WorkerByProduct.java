package com.ip.pi_kurs.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkerByProduct {
    private int id;
    private int workerId;
    private int productId;
    private String workerName;
    private Timestamp period;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
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
