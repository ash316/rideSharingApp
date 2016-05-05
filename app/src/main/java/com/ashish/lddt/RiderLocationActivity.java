package com.ashish.lddt;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RiderLocationActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private ImageButton campus;
    private ImageButton home;
    private LocationManager locationManager;
    private TextView txtview;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_location);
        setTitle("Nearby Riders");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        campus  = (ImageButton) findViewById(R.id.imageButton);
        campus.setOnClickListener(this);
        home  = (ImageButton) findViewById(R.id.imageButton2);
        home.setOnClickListener(this);

        txtview = (TextView) findViewById(R.id.editText);
        txtview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX()  >= txtview.getRight() - txtview.getTotalPaddingRight()) {

                        // Get the location manager
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        try {
                            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location == null) {
                                txtview.setText("Unable to determine current location.");
                            } else {
                                txtview.setText(location.getLatitude() + "," + location.getLongitude());
                            }
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }
                return true;
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                try {
                    LatLng bundoora = new LatLng(-37.6767819, 145.0733402);
                    mMap.addMarker(new MarkerOptions()
                            .position(bundoora)
                            .title("Campus")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))).showInfoWindow();

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bundoora, 14.5f));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imageButton2:
                LatLng home = new LatLng(-37.813255, 145.008981);
                mMap.addMarker(new MarkerOptions()
                        .position(home)
                        .title("Home")).showInfoWindow();

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 14.5f));

                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng bundoora = new LatLng(-37.6767819, 145.0733402);
        mMap.addMarker(new MarkerOptions().position(bundoora).title("Rick").icon(BitmapDescriptorFactory.fromResource(R.drawable.car))).showInfoWindow();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-37.678643, 145.069709))
                .title("Mark")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                ).showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-37.674914, 145.068485))
                .title("John")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
        ).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bundoora, 14.5f));
    }
}
