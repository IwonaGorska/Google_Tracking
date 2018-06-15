package com.example.lenovo_pc.ros_media_ig;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by LENOVO-PC on 15.06.2018.
 */

public class Start extends MapsActivity
{
    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT); //added
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);}

    private void addLocationListener(LocationListener locationListener) {
        LocationProvider locationProvider = getLocationManager().getProvider(LocationManager.GPS_PROVIDER);

        getLocationManager().requestLocationUpdates(locationProvider.getName(), LOCATION_UPDATE_INTERVAL,
                LOCATION_UPDATE_MIN_DISTANCE, locationListener);
    }

    private LocationManager getLocationManager() {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startGpsListening(Location start) {
        this.startLocation = start;
        addLocationListener(new MyLocationListener());
    }

    private void redrawLine(){
        myMap.clear();  //clears all Markers and Polylines
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        //addMarker(); //add Marker in current position - maybe resign now
        line = myMap.addPolyline(options); //add Polyline
    }
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude); //you already have this
        points.add(latLng); //added
        redrawLine(); //added
    }
}