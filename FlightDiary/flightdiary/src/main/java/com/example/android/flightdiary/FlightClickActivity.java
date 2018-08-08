package com.example.android.flightdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.TextView;

public class FlightClickActivity extends AppCompatActivity {

    private int databaseID;
    private TextView flightNumber;
    private TextView flightDate;
    private TextView flightType;
    private TextView flightReg;
    private Button airframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_click);

        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            databaseID = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        }

        flightNumber = (TextView) findViewById(R.id.FlightDetailNumber);

        flightDate = (TextView) findViewById(R.id.FlightDetailDate);

        flightReg = (TextView) findViewById(R.id.FlightDetailReg);

        flightType = (TextView) findViewById(R.id.FlightDetailType);


        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flightclick_menu, menu);
        return true;
    }


}
