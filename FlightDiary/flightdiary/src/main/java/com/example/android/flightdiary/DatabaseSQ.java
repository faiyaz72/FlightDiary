package com.example.android.flightdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

/**
 * Created by FaiyazHuq on 2018-08-02.
 */

public class DatabaseSQ extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FlightDatabase.db";
    public static final String TABLE_FLIGHTS = "flights";
    private static final int DATABASE_VERSION = 1;
    public static final String FLIGHT_COLUMN_ID = "_id";
    public static final String FLIGHT_NUMBER = "number";
    public static final String FLIGHT_DATE = "date";
    public static final String FLIGHT_REG = "reg";
    public static final String FLIGHT_AIRLINE = "airline";
    public static final String FLIGHT_TYPE = "type";
    public static final String FLIGHT_IMAGE_PATH = "image";
    public static final String ID = "id";

    public DatabaseSQ(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_FLIGHTS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FLIGHT_COLUMN_ID + " INTEGER, " +
                FLIGHT_NUMBER + " TEXT, " +
                FLIGHT_DATE + " TEXT, " +
                FLIGHT_REG + " TEXT, " +
                FLIGHT_AIRLINE + " TEXT, " +
                FLIGHT_TYPE + " TEXT, " +
                FLIGHT_IMAGE_PATH + " TEXT " +
                ");";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_FLIGHTS);
        onCreate(db);
    }

//    public void insertFlight(Flight toADD) {
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(FLIGHT_COLUMN_ID, toADD.getId());
//        contentValues.put(FLIGHT_NUMBER, toADD.getFlightNumber());
//        contentValues.put(FLIGHT_DATE, toADD.getDate());
//        contentValues.put(FLIGHT_REG, toADD.getReg());
//        contentValues.put(FLIGHT_AIRLINE, toADD.getAirline());
//        contentValues.put(FLIGHT_TYPE, toADD.getType());
//        contentValues.put(FLIGHT_IMAGE_PATH, toADD.getImagePath());
//
//        SQLiteDatabase db = getWritableDatabase();
//
//
//
//
//        db.insert(TABLE_FLIGHTS, null, contentValues);
//        db.close();
//
//    }

    public boolean updatePerson(Flight toADD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FLIGHT_NUMBER, toADD.getFlightNumber());
        contentValues.put(FLIGHT_DATE, toADD.getDate());
        contentValues.put(FLIGHT_REG, toADD.getReg());
        contentValues.put(FLIGHT_AIRLINE, toADD.getAirline());
        contentValues.put(FLIGHT_TYPE, toADD.getType());
        contentValues.put(FLIGHT_IMAGE_PATH, toADD.getImagePath());

        db.update(DATABASE_NAME, contentValues, FLIGHT_COLUMN_ID + " = ? ", new String[] { Integer.toString(toADD.getId()) } );
        return true;
    }

    public Integer deleteFlight(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATABASE_NAME,
                FLIGHT_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

//    public String databaseToString() {
//
//        String dbString = "";
//        SQLiteDatabase db = getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_FLIGHTS + ";";
//        Cursor c = db.rawQuery(query, null);
//
//        //db.query(TABLE_FLIGHTS, null,null,null,null,null,null);
//        c.moveToFirst();
//
//        while(!c.isAfterLast()) {
//            if(c.getString(c.getColumnIndex(FLIGHT_NUMBER)) != null) {
//                dbString += c.getString(c.getColumnIndex(FLIGHT_NUMBER));
//                dbString += "\n";
//            }
//            c.moveToNext();
//        }
//        db.close();
//        return dbString;
//    }
}
