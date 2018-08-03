package com.example.android.flightdiary;

import android.widget.ImageView;

/**
 * Created by FaiyazHuq on 2018-08-01.
 */

public class Flight {

    public static int incr_id;

    private String flightNumber;

    private String date;

    private String reg;

    private String airline;

    private String type;

    private String imagePath;

    private int id;


    public Flight(String flightNumber, String date, String reg, String airline, String type,
                  String imageResourceID) {

        this. flightNumber = flightNumber;
        this.date = date;
        this.reg = reg;
        this.airline = airline;
        this.type = type;
        this.imagePath = imageResourceID;
        this.id = incr_id;
        incr_id = incr_id + 1;

    }


    public String getFlightNumber() {
        return flightNumber;
    }

    public int getId() {
        return id;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
