package com.example.android.flightdiary;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_AIRLINE;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_COLUMN_ID;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_DATE;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_IMAGE_PATH;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_NUMBER;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_REG;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_TYPE;
import static com.example.android.flightdiary.DatabaseSQ.TABLE_FLIGHTS;
import static com.example.android.flightdiary.MainActivity.REQUEST_TAKE_PHOTO;

public class AddActivity extends AppCompatActivity {

    private EditText nameText;

    //public static Flight temp;

    private EditText dateText;

    private EditText airlineText;

    private EditText typeText;

    private EditText regText;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //private Button add;

    private Button image;

    private ImageView imageView;
    private String mCurrentPhotoPath;
    private DatabaseSQ dbHandler;
    private TextView showData;

    private SQLiteDatabase db;

    private MenuView.ItemView done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameText = (EditText) findViewById(R.id.nameText);

        dateText = (EditText) findViewById(R.id.dateText);

        typeText = (EditText) findViewById(R.id.typeText);

        airlineText = (EditText) findViewById(R.id.airlineText);

        regText = (EditText) findViewById(R.id.regText);

        //add = (Button) findViewById(R.id.AddButton);

        dbHandler = new DatabaseSQ(this);


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                dateText.setText(date);
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);

        return true;
    }


    private void insertFlight(Flight toADD) {

        dbHandler = new DatabaseSQ(this);

        db = dbHandler.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(FLIGHT_COLUMN_ID, toADD.getId());
        contentValues.put(FLIGHT_NUMBER, toADD.getFlightNumber());
        contentValues.put(FLIGHT_DATE, toADD.getDate());
        contentValues.put(FLIGHT_REG, toADD.getReg());
        contentValues.put(FLIGHT_AIRLINE, toADD.getAirline());
        contentValues.put(FLIGHT_TYPE, toADD.getType());
        contentValues.put(FLIGHT_IMAGE_PATH, toADD.getImagePath());


        db.insert(TABLE_FLIGHTS, null, contentValues);


    }


    public void AddFlight(MenuItem item) {

        String name = nameText.getText().toString().trim();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(name, "")) {
                Toast.makeText(this, "Flight Number mandatory", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String date = dateText.getText().toString().trim();
        String airline = airlineText.getText().toString().trim();
        String type = typeText.getText().toString().trim();
        String reg = regText.getText().toString().trim();


        Context context = this;
        Class destination = ImageAddActivity.class;

        Intent goToImageClick = new Intent(context, destination);


        //Flight newFlight = new Flight(name, date, reg, airline, type, mCurrentPhotoPath);

        insertFlight(new Flight(name, date, reg, airline, type, mCurrentPhotoPath));
        startActivity(goToImageClick);

    }
}
