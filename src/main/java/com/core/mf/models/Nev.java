package com.core.mf.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nev{
    private LocalDate date;
    private float navValue;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public float getNavValue() {
        return navValue;
    }

    public void setNavValue(float navValue) {
        this.navValue = navValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Nev(String date, Float navValue){
        this.date = LocalDate.parse(date, Nev.formatter);
        this.navValue = navValue;
    }
}