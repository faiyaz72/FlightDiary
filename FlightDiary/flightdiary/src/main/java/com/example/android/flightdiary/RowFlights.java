package com.example.android.flightdiary;

/**
 * Created by FaiyazHuq on 2018-08-07.
 */

public class RowFlights {

    private String flightNumber;
    private String date;
    private int databaseID;

    public RowFlights(String flightNumber, String date, int databaseID) {

        this.flightNumber = flightNumber;
        this.date = date;
        this.databaseID = databaseID;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDate() {
        return date;
    }

    public int getDatabaseID() {
        return databaseID;
    }
}
