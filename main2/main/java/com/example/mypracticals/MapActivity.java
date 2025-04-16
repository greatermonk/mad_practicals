package com.example.mypracticals;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import timber.log.Timber;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    // UI Components
    private BottomNavigationView navigationView;
    private TextInputEditText startLocationInput;
    private TextInputEditText endLocationInput;
    private MaterialButton calculateRouteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // Initialize Timber for logging
//        Timber.plant(new Timber.DebugTree());
        // Initialize Location Services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Initialize Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        // Initialize UI Components
        initializeUIComponents();

        // Setup Listeners
        setupListeners();
    }

    private void initializeUIComponents() {
        navigationView = findViewById(R.id.navigationBar);
        startLocationInput = findViewById(R.id.startLocationInput);
        endLocationInput = findViewById(R.id.endLocationInput);
        calculateRouteButton = findViewById(R.id.calculateRouteButton);
    }
    private void calculateRoute() {
        String startLocation = Objects.requireNonNull(startLocationInput.getText()).toString().trim();
        String endLocation = Objects.requireNonNull(endLocationInput.getText()).toString().trim();

        if (startLocation.isEmpty() || endLocation.isEmpty()) {
            Toast.makeText(this, "Please enter both start and end locations", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use Geocoder to convert location names to coordinates
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> startAddresses = geocoder.getFromLocationName(startLocation, 1);
            List<Address> endAddresses = geocoder.getFromLocationName(endLocation, 1);

            assert startAddresses != null;
            if (startAddresses.isEmpty() || Objects.requireNonNull(endAddresses).isEmpty()) {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                return;
            }

            Address startAddress = startAddresses.get(0);
            Address endAddress = endAddresses.get(0);

            LatLng startLatLng = new LatLng(startAddress.getLatitude(), startAddress.getLongitude());
            LatLng endLatLng = new LatLng(endAddress.getLatitude(), endAddress.getLongitude());

            // Clear previous map markers and routes
            mMap.clear();

            // Add markers for start and end locations
            mMap.addMarker(new MarkerOptions().position(startLatLng).title("Start"));
            mMap.addMarker(new MarkerOptions().position(endLatLng).title("End"));

            // Move camera to show entire route
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(startLatLng);
            builder.include(endLatLng);
            LatLngBounds bounds = builder.build();

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));


        } catch (IOException e) {
            Timber.e(e, "Geocoding error");
            Toast.makeText(this, "Error finding locations", Toast.LENGTH_SHORT).show();
        }
    }
    private void setupListeners() {
        // Bottom Navigation Listener
        navigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_current_location) {
                getCurrentLocation();
                return true;
            } else if (itemId == R.id.action_route_planning) {
                // Show route planning UI
                toggleRoutePlanningVisibility();
                return true;
            }
            return false;
        });
        // Calculate Route Button Listener
        calculateRouteButton.setOnClickListener(v -> calculateRoute());
    }

    private void toggleRoutePlanningVisibility() {
        int visibility = View.VISIBLE;
        startLocationInput.setVisibility(visibility);
        endLocationInput.setVisibility(visibility);
        calculateRouteButton.setVisibility(visibility);
    }


    private void getCurrentLocation() {
        // Check location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Get last known location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null && mMap != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                        // Clear previous markers
                        mMap.clear();

                        // Add marker and move camera
                        mMap.addMarker(new MarkerOptions()
                                .position(currentLocation)
                                .title("My Location"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

                        Timber.d("Current Location: %s, %s",
                                location.getLatitude(), location.getLongitude());
                    }
                });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Check and request location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get current location
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}