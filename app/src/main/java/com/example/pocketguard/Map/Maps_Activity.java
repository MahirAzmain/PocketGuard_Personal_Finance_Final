package com.example.pocketguard.Map;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.pocketguard.R;
import com.example.pocketguard.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Activity for displaying a map with a marker at a specific location.
 *
 * <p><b>Design Patterns Used:</b></p>
 * <ul>
 *     <li><b>Model-View-Controller (MVC):</b> This class serves as the controller, managing interactions between the model (location data) and the view (Google Map).</li>
 *     <li><b>Observer:</b> The OnMapReadyCallback interface is implemented to observe the map's readiness and perform actions once it's ready.</li>
 * </ul>
 */
public class Maps_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Double latitude,longitidu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve latitude and longitude from intent extras
        latitude = getIntent().getDoubleExtra("LAT",1);
        longitidu = getIntent().getDoubleExtra("LON",1);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, a marker is added at the specified location.
     *
     * @param googleMap The GoogleMap object representing the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at the specified location
        Log.d("location_map", "onLocationResult: "+latitude+" "+longitidu);
        LatLng my_loc = new LatLng(latitude, longitidu);

        Log.d("Tesssssss", "onMapReady: "+latitude+","+longitidu);

        mMap.addMarker(new MarkerOptions().position(my_loc).title("Child").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_new)));
        // Move the camera to the specified location and zoom in
        CameraPosition cameraPosition = new CameraPosition.Builder().target(my_loc).zoom(17).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(my_loc));

    }
}