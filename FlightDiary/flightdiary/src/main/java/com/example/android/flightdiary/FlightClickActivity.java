package com.example.android.flightdiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_DATE;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_IMAGE_PATH;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_NUMBER;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_REG;
import static com.example.android.flightdiary.DatabaseSQ.FLIGHT_TYPE;
import static com.example.android.flightdiary.DatabaseSQ.ID;
import static com.example.android.flightdiary.DatabaseSQ.TABLE_FLIGHTS;

public class FlightClickActivity extends AppCompatActivity {

    private int databaseID;
    private TextView flightNumber;
    private TextView flightDate;
    private TextView flightType;
    private TextView flightReg;
    private ImageView flightImage;
    private ImageView logoImage;
    private Button airframe;
    DatabaseSQ dbHandler;
    SQLiteDatabase db;

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

        flightImage = (ImageView) findViewById(R.id.flightDetailImage);

        logoImage = (ImageView) findViewById(R.id.flightDetailAirlineLogo);

        dbHandler = new DatabaseSQ(this);

        
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            showDetails();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.flightclick_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDetails() {

        db = dbHandler.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_FLIGHTS + " WHERE " + ID + " == " + databaseID + ";";
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        String number = c.getString(c.getColumnIndex(FLIGHT_NUMBER));
        String date = c.getString(c.getColumnIndex(FLIGHT_DATE));
        String reg = c.getString(c.getColumnIndex(FLIGHT_REG));
        String type = c.getString(c.getColumnIndex(FLIGHT_TYPE));
        String imageURL = c.getString(c.getColumnIndex(FLIGHT_IMAGE_PATH));

        flightNumber.setText(number);
        flightDate.setText(date);
        flightReg.setText(reg);
        flightType.setText(type);
        if (imageURL != null) {
            setPic(imageURL);
        }
        loadMapPreview(number.substring(0, 2), logoImage);

    }

    private void setPic(String PhotoPath) {
        // Get the dimensions of the View
        int targetW = flightImage.getWidth();
        int targetH = flightImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(PhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoH/targetH, photoW/targetW);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(PhotoPath, bmOptions);
        flightImage.setImageBitmap(bitmap);

        //add.setVisibility(View.VISIBLE);

    }


    public void modifyFlight(MenuItem item) {

    }

    private void loadMapPreview (final String airlineName, final ImageView imageView) {
        //start a background thread for networking
        new Thread(new Runnable() {
            public void run(){
                try {
                    //download the drawable
                    final Drawable drawable = Drawable.createFromStream((InputStream)
                            new URL("https://daisycon.io/images/airline/?width=300&height=150&color=ffffff&iata="+airlineName).getContent(), "src");
                    //edit the view in the UI thread
                    imageView.post(new Runnable() {
                        public void run() {
                            imageView.setImageDrawable(drawable);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
