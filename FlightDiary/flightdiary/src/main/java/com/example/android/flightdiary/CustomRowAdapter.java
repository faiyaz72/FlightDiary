package com.example.android.flightdiary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by FaiyazHuq on 2018-08-07.
 */

public class CustomRowAdapter extends ArrayAdapter<RowFlights> {


    public CustomRowAdapter(@NonNull Context context, ArrayList<RowFlights> flights) {
        super(context, R.layout.custom_row, flights);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflator = LayoutInflater.from(getContext());
        View customView = inflator.inflate(R.layout.custom_row, parent, false);

        RowFlights getFlight = getItem(position);
        TextView flightNumber = (TextView) customView.findViewById(R.id.rowID);
        String airlineLogo = getFlight.getFlightNumber().substring(0, 2);

        TextView date = (TextView) customView.findViewById(R.id.rowDate);

        ImageView imageRow = (ImageView) customView.findViewById(R.id.rowImage);

        flightNumber.setText(getFlight.getFlightNumber());
        date.setText(getFlight.getDate());

        //imageRow.setImageResource(R.drawable.ek); //TODO fix this so that it displays correct airline logo

        loadMapPreview(airlineLogo, imageRow);

        return customView;


    }

    private void loadMapPreview (final String airlineName, final ImageView imageView) {
        //start a background thread for networking
        new Thread(new Runnable() {
            public void run(){
                try {
                    //download the drawable
                    final Drawable drawable = Drawable.createFromStream((InputStream) new URL("https://daisycon.io/images/airline/?width=300&height=150&color=ffffff&iata="+airlineName).getContent(), "src");
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
