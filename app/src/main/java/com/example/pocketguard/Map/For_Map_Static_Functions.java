package com.example.pocketguard.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.pocketguard.Key_Static_Methods;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A utility class for managing location-related functionality.
 * This class initializes location services, requests location updates,
 * and processes the received location data.
 *
 * <p><b>Design Patterns Used:</b></p>
 * <ul>
 *     <li><b>Singleton:</b> The location services are initialized in a static method, ensuring only one instance is used.</li>
 *     <li><b>Facade:</b> This class provides a simplified interface to the complex subsystem of location services.</li>
 *     <li><b>Adapter:</b> The Geocoder class is used to adapt the latitude and longitude coordinates to human-readable addresses.</li>
 *     <li><b>Observer:</b> The LocationCallback is an implementation of the observer pattern, reacting to location updates.</li>
 * </ul>
 */
public class For_Map_Static_Functions {

    private static FusedLocationProviderClient fusedLocationProviderClient;
    private static LocationRequest locationRequest;
    private static LocationCallback locationCallback;
    private static double latitude,longitude;

    private static Geocoder geocoder;
    private static List<Address> addresses;
    /**
     * Initializes location services and starts requesting location updates.
     *
     * @param context The application context.
     */
    public static void location_initialize(Context context){

        geocoder = new Geocoder(context, Locale.ENGLISH);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if(locationResult != null){
                    for(Location location:locationResult.getLocations()){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        String address="";
                        try {
                            addresses = geocoder.getFromLocation(latitude,longitude,1);
                            address  = addresses.get(0).getLocality()+", "+addresses.get(0).getCountryName();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Location_Make location_make = new Location_Make(String.valueOf(latitude),String.valueOf(longitude));
                        Key_Static_Methods.sending_data_to_firebase(context,null,location_make);//Location is Updating

                        //Toast.makeText(context, ""+address, Toast.LENGTH_SHORT).show();

                        Log.d("location_1111", "onLocationResult: "+latitude+" "+longitude);
                        Log.d("location_1111", "onLocationResult: "+address);
                    }
                }

            }
        };
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        createLocationRequest(context);
    }

    /**
     * Creates and configures a location request.
     *
     * @param context The application context.
     */


    @SuppressLint("MissingPermission")
    private static void createLocationRequest(Context context) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(8000);//eta milisecond e dita hoba //Mane koto milisecond por por location diba amake
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);  // Mane eta set korba acuracy ki high hoba naki low hoba
        locationRequest.setFastestInterval(8000); // Mane doro same location 5 ta app use korsa,, to tomar app koto somoyer modda data update kora amake dakabe

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},101);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);

    }

}