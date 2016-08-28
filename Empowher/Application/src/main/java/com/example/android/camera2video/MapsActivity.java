package com.x10host.dhanushpatel.empowher;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, android.location.LocationListener {

    private GoogleMap mMap;
    double latitude;
    double longitude;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onLocationChanged(Location location) {
//        TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        double latitude1 = location.getLatitude();
        double longitude1 = location.getLongitude();
        LatLng latLng = new LatLng(latitude1, longitude1);
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        LatLng sydney = new LatLng(latitude1, longitude1);
        mMap.addMarker(new MarkerOptions().position(sydney).title("1 Hacker Way, Menlo Park, CA 94025, USA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
//        setContentView(R.layout.activity_my);
//        SupportMapFragment supportMapFragment =
//                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
//        mMap = supportMapFragment.getMap();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, MapsActivity.this);

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(37.484556, -122.147845))
                .strokeWidth(10)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(100,0,255,255))
                .radius(1000); // In meters

        // Get back the mutable Circle
        Circle circle = mMap.addCircle(circleOptions);


        // Add a marker in Sydney and move the camera
        // Instantiates a new CircleOptions object and defines the center and radius
//        CircleOptions circleOptions = new CircleOptions()
//                .center(new LatLng(latitude, longitude))
//                .strokeWidth(10)
//                .strokeColor(Color.RED)
//                .fillColor(Color.CYAN)
//                .radius(1000); // In meters
//
//        // Get back the mutable Circle
//        Circle circle = mMap.addCircle(circleOptions);
//        LatLng sydney = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("1 Hacker Way, Menlo Park, CA 94025, USA"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
