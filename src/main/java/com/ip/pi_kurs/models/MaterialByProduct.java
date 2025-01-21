package com.ip.pi_kurs.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MaterialByProduct {
    private int id;
    private int productId;
    private int materialId;
    private String materialName;
    private int number;
    private Timestamp period;
    private String periodString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
