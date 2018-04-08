package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Ola on 03.02.2018.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    //We will be using the split(String string) method in the String class to split the original string at the position
    // where the text “ of “ occurs. The result will be a String containing the characters PRIOR to the “ of ” text and
    // a String containing the characters AFTER the “ of “ text. Since we’ll frequently need to refer to the “ of “ text,
    // we can define a static final String constant (that is a global variable) at the top of the EarthquakeAdapter class
    private static final String LOCATION_SEPARATOR = " of ";

    /**
     * Constructs a new {@link EarthquakeAdapter}.
     *
     * @param context of the app
     * @param earthquakes is the list of earthquakes, which is the data source of the adapter
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context,0,earthquakes);
    }

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_earthquake,parent,
                    false);

            //Find the earthquake at the given position in the list of earthquakes
            final Earthquake currentEarthquake = getItem(position);

            // we get the original location String from the Earthquake object and store that in a variable originalLocation /placeInfo
            String placeInfo = currentEarthquake.getEarthquakePlace();
            // We create new variables (primary location/parts1 and location offset/parts2) to store the resulting Strings
            String parts1; //primaryLocation
            String parts2; //locationOffset

            //we should check if the original location String contains the LOCATION_SEPARATOR first, before we decide to split the string with that separator
            //if (originalLocation.contains(LOCATION_SEPARATOR)) {
            //    String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            //    locationOffset = parts[0] + LOCATION_SEPARATOR;
            //    primaryLocation = parts[1];
           // } else {
            //    locationOffset = getContext().getString(R.string.near_the);
            //    primaryLocation = originalLocation;
           // }

            if(placeInfo.contains(LOCATION_SEPARATOR)){
                String[] parts = placeInfo.split(LOCATION_SEPARATOR);
                parts1 = parts[0] + LOCATION_SEPARATOR;
                parts2 = parts[1];
            } else {
                parts1 = getContext().getString(R.string.near_the);
                parts2 = placeInfo;
            }

            //Once we have the 2 separate Strings, we can display them in the 2 TextViews in the list item layout
            //TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
            //primaryLocationView.setText(primaryLocation);
            //TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
            //locationOffsetView.setText(locationOffset);


            TextView where = (TextView) listItemView.findViewById(R.id.where);
            where.setText(parts1);
            // Find the TextView with view ID location/place
            //TextView locationView = (TextView) listItemView.findViewById(R.id.location);
            TextView place = (TextView) listItemView.findViewById(R.id.place);
            // Display the location of the current earthquake in that TextView
            //locationView.setText(currentEarthquake.getLocation());
            place.setText(parts2);

            // Find the TextView with view ID magnitude
            //TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
            //magnitudeView.setText(currentEarthquake.getMagnitude());
            TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
            String earthquakeMagnitude = formatMagnitude(currentEarthquake.getEarthquakeMagnitude());
            // Display the magnitude of the current earthquake in that TextView
            magnitude.setText(earthquakeMagnitude);

            //Create a new Date object from the time in milliseconds of the earthquake
            Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

            /// Find the TextView with view ID time
            TextView time = (TextView) listItemView.findViewById(R.id.time);
            // Format the time string (i.e. "4:30PM")
            //String formattedTime = formatTime(dateObject);
            String earthquakeTime = formatTime(dateObject);
            // Display the time of the current earthquake in that TextView
            time.setText(earthquakeTime);

            //Find the TextView with view ID date
            //TextView dateView = (TextView) listItemView.findViewById(R.id.date);
            TextView date = (TextView) listItemView.findViewById(R.id.date);
            //Format the date string (i.e. "Mar 3, 1984")
            //String formattedDate = formatDate(dateObject);
            String earthquakeDate = formatDate(dateObject);
            //Display the date of the current earthquake in that TextView
            date.setText(earthquakeDate);

            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            int magnitudeColor = getMagnitudeColor(currentEarthquake.getEarthquakeMagnitude());

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);

        }
        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the color for the magnitude circle based on the intensity of the earthquake.
     *
     * @param magnitude of the earthquake
     */
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude){
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(magnitude);
    }


}