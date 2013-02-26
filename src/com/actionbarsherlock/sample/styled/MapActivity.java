package com.actionbarsherlock.sample.styled;


import twitter4j.GeoLocation;
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
	private static final LatLng PHILADELPHIA = new LatLng(39.9522, -75.1642);
	private static GeoLocation atlanta = new GeoLocation(33.65, -84.42);
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




		//    Marker marker = map.addMarker(new MarkerOptions()
		//    .position(myLocation) //.position(PHILADELPHIA)
		//    .title("Philly")
		//    .snippet("Home Sweet Home")
		//    .icon(BitmapDescriptorFactory.fromResource(R.drawable.target)));

		GeoLocation[] mLocations = TweetReader.getLocations();
		String[] mTweets = TweetReader.getTweets();
		String[] mUsers = TweetReader.getUsers();
		int numLocations = mLocations.length;
		double lat, lng;

		for(int i = 0; i < numLocations; i++ ) {

			lat = mLocations[i].getLatitude();
			lng = mLocations[i].getLongitude();
			
			if ((lat == atlanta.getLatitude())
					&& (lng == atlanta.getLongitude()))
				continue;

			map.addMarker(new MarkerOptions()
			.position(new LatLng(lat, lng))
			.title(mUsers[i])
			.snippet(mTweets[i])
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.target)));
		}







		// Move the camera instantly to philadelphia with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(PHILADELPHIA, 15));

		//		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

		// Zoom in, animating the camera.
		//    map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

} 
