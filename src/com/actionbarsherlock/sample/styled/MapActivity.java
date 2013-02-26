package com.actionbarsherlock.sample.styled;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
  static final LatLng PHILADELPHIA = new LatLng(39.9522, -75.1642);
  private GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getIntent().getExtras();
    double mLat = bundle.getDouble("mLat");
    double mLong = bundle.getDouble("mLong");
    setContentView(R.layout.map_layout);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();

    LatLng myLocation = new LatLng(mLat, mLong);
        
    Marker marker = map.addMarker(new MarkerOptions()
    .position(myLocation) //.position(PHILADELPHIA)
    .title("Philly")
    .snippet("Home Sweet Home")
    .icon(BitmapDescriptorFactory.fromResource(R.drawable.target)));
    
    

    // Move the camera instantly to philadelphia with a zoom of 13.
//    map.moveCamera(CameraUpdateFactory.newLatLngZoom(PHILADELPHIA, 13));
    
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

    // Zoom in, animating the camera.
//    map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

} 
