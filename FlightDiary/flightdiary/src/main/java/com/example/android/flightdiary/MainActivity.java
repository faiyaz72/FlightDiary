package com.example.android.flightdiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_AIRLINE;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_DATE;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_NUMBER;
import static com.example.android.flightdiary.DatabaseSQ.ID;
import static com.example.android.flightdiary.DatabaseSQ.TABLE_FLIGHTS;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 0;
    static final int REQUEST_TAKE_PHOTO = 1;
    private TextView flightMain;
    private TextView dateMain;
    private TextView airlineMain;
    private TextView totalData;


    DatabaseSQ dbHandler;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flightMain = (TextView) findViewById(R.id.FlightNumberMain);
        dateMain = (TextView) findViewById(R.id.dateMain);
        airlineMain = (TextView) findViewById(R.id.AirlineMain);
        totalData = (TextView) findViewById(R.id.totalData);

        dbHandler = new DatabaseSQ(this);

        if (ContextCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
//        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        showLastFlight();
        showTotalCount();
        //showData.setText(databaseToString());
    }

    private void showTotalCount() {


        db = dbHandler.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_FLIGHTS + ";";
        Cursor c = db.rawQuery(query, null);

        int total = c.getCount();

        String message = "Total flights in Database: " + total;

        totalData.setText(message);


    }

    @SuppressLint("SetTextI18n")
    private void showLastFlight() {
        db = dbHandler.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_FLIGHTS + ";";
        Cursor c = db.rawQuery(query, null);

        //get most recent flight that has been added

        if(c.getCount() != 0) {
            c.moveToLast();
            airlineMain.setVisibility(View.VISIBLE);
            dateMain.setVisibility(View.VISIBLE);


            //get ID for the most recent flight

            String flightNumber = c.getString(c.getColumnIndex(FLIGHT_NUMBER));
            String airline = c.getString(c.getColumnIndex(FLIGHT_AIRLINE));
            String date = c.getString(c.getColumnIndex(FLIGHT_DATE));

            flightMain.setText(flightNumber);
            airlineMain.setText(airline);
            dateMain.setText(date);
            c.close();
        } else {

            flightMain.setText("Add your First Flight!");
            airlineMain.setVisibility(View.INVISIBLE);
            dateMain.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menue, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add) {

            Context context = MainActivity.this;
            Class destination = AddActivity.class;

            Intent startChildActivity = new Intent(context, destination);

            startActivity(startChildActivity);

        }
        return true;
    }

    private String databaseToString() {

        String dbString = "";
        db = dbHandler.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FLIGHTS + ";";
        Cursor c = db.rawQuery(query, null);

        //db.query(TABLE_FLIGHTS, null,null,null,null,null,null);
        c.moveToFirst();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(FLIGHT_NUMBER)) != null) {
                dbString += c.getString(c.getColumnIndex(FLIGHT_NUMBER));
                dbString += "\n";
            }
            c.moveToNext();
        }
        return dbString;
    }
}
