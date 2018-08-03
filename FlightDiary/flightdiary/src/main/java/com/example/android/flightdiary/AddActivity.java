package com.example.android.flightdiary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.android.flightdiary.MainActivity.REQUEST_TAKE_PHOTO;

public class AddActivity extends AppCompatActivity {

    private EditText nameText;

    private EditText dateText;

    private EditText airlineText;

    private EditText typeText;

    private EditText regText;

    private Button add;

    private Button image;

    private ImageView imageView;
    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameText = (EditText) findViewById(R.id.nameText);

        dateText = (EditText) findViewById(R.id.dateText);

        typeText = (EditText) findViewById(R.id.typeText);

        airlineText = (EditText) findViewById(R.id.airlineText);

        regText = (EditText) findViewById(R.id.regText);

        add = (Button) findViewById(R.id.AddButton);

        image = (Button) findViewById(R.id.ImageButton);

        imageView = (ImageView) findViewById(R.id.imageView);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void AddFlightImage(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Error cannot find camera", Toast.LENGTH_LONG)
                        .show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.flightdiary",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("Reached Here");
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        }
    }


    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoH/targetH, photoW/targetW);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);

        add.setVisibility(View.VISIBLE);

    }

    public void AddFlight(View view) {

        String name = nameText.getText().toString();
        String date = dateText.getText().toString();
        String airline = airlineText.getText().toString();
        String type = typeText.getText().toString();
        String reg = regText.getText().toString();


        Context context = this;
        Class destination = MainActivity.class;

        Intent goBackToMain = new Intent(context, destination);


        Flight newFlight = new Flight(name, date, reg, airline, type, mCurrentPhotoPath);

        startActivity(goBackToMain);
    }
}
