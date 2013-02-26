/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionbarsherlock.sample.styled;

import twitter4j.Twitter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

public class StyledActivity extends SherlockFragmentActivity 
implements ActionBar.TabListener {
	private static final String TAG = "StyledActivity";

	private final Handler handler = new Handler();

	private FragmentManager fm;

	private static int mPosition = 1;

	private static Twitter mTwitter; 


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		mTwitter = ((TwitterApplication)getApplication()).getTwitter();

		Log.d(TAG, "We went through onCreate");

		fm = getSupportFragmentManager();

		setUpActionBar();

	}



	public void setUpActionBar(){
		final ActionBar ab = getSupportActionBar();

		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayShowTitleEnabled(false);


		switch (mPosition) { 
		case 0:
			ab.addTab(ab.newTab().setText("Profile").setTabListener(this), true);
			ab.addTab(ab.newTab().setText("TownHall").setTabListener(this));
			ab.addTab(ab.newTab().setText("Filter").setTabListener(this));
			break;
		case 1:
			ab.addTab(ab.newTab().setText("Profile").setTabListener(this));
			ab.addTab(ab.newTab().setText("TownHall").setTabListener(this), true);
			ab.addTab(ab.newTab().setText("Filter").setTabListener(this));
			break;
		case 2:
			ab.addTab(ab.newTab().setText("Profile").setTabListener(this));
			ab.addTab(ab.newTab().setText("TownHall").setTabListener(this));
			ab.addTab(ab.newTab().setText("Filter").setTabListener(this), true);
			break;
		}


		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);

		// set up a listener for the refresh item
		final MenuItem refresh = (MenuItem) menu.findItem(R.id.menu_refresh);
		refresh.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			// on selecting show progress spinner for 1s
			public boolean onMenuItemClick(MenuItem item) {
				// item.setActionView(R.layout.progress_action);
				handler.postDelayed(new Runnable() {
					public void run() {
						refresh.setActionView(null);
					}
				}, 1000);
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(getApplicationContext(), 
					"We clicked the icon", Toast.LENGTH_SHORT).show();
			return false;
		case R.id.menu_refresh:
			// switch to a progress animation
			item.setActionView(R.layout.indeterminate_progress_action);
			return true;
		case R.id.menu_nav_tabs:
			item.setChecked(true);
			showTabsNav();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	private void showTabsNav() {

	}

	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		Log.d(TAG, "onTabSelected: " + tab.getText());

		mPosition = tab.getPosition();


		ProfileFragment profile = 
				(ProfileFragment) fm.findFragmentByTag("profile");
		TownhallFragment townhall = 
				(TownhallFragment) fm.findFragmentByTag("townhall");
		FilterFragment filter = 
				(FilterFragment) fm.findFragmentByTag("filter");


		switch (mPosition) { 
		case 0:

			ProfileFragment newProfile = 
			ProfileFragment.newInstance((int)(Math.random()*19)); 

			if(profile != null)
				fm.beginTransaction().remove(profile).commit();
			if(townhall != null)
				fm.beginTransaction().remove(townhall).commit();
			if(filter != null)
				fm.beginTransaction().remove(filter).commit();

			fm.beginTransaction().add(
					android.R.id.content, newProfile, "profile").commit();

			break;
		case 1:

			TownhallFragment newTownhall = new TownhallFragment();

			if(profile != null)
				fm.beginTransaction().remove(profile).commit();
			if(townhall != null)
				fm.beginTransaction().remove(townhall).commit();
			if(filter != null)
				fm.beginTransaction().remove(filter).commit();



			fm.beginTransaction().add(
					android.R.id.content, newTownhall, "townhall").commit();

			break;
		case 2:

			FilterFragment newFilter = new FilterFragment();

			if(profile != null)
				fm.beginTransaction().remove(profile).commit();
			if(townhall != null)
				fm.beginTransaction().remove(townhall).commit();
			if(filter != null)
				fm.beginTransaction().remove(filter).commit();

			fm.beginTransaction().add(
					android.R.id.content, newFilter, "filter").commit();
			break;

		default:
			assert(false);
		}

	}

	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		Log.d(TAG, "onTabUnselected: " + tab.getText());
		Log.d(TAG, "previousTab: " + tab.getPosition());



	}

	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		Log.d(TAG, "onTabReselected: " + tab.getText());
		
		mPosition = tab.getPosition();


		ProfileFragment profile = 
				(ProfileFragment) fm.findFragmentByTag("profile");
		TownhallFragment townhall = 
				(TownhallFragment) fm.findFragmentByTag("townhall");
		FilterFragment filter = 
				(FilterFragment) fm.findFragmentByTag("filter");


		switch (mPosition) { 
		case 0:

			break;
		case 1:

			TownhallFragment newTownhall = new TownhallFragment();

			if(profile != null)
				fm.beginTransaction().remove(profile).commit();
			if(townhall != null)
				fm.beginTransaction().remove(townhall).commit();
			if(filter != null)
				fm.beginTransaction().remove(filter).commit();



			fm.beginTransaction().add(
					android.R.id.content, newTownhall, "townhall").commit();

			break;
		case 2:

			FilterFragment newFilter = new FilterFragment();

			if(profile != null)
				fm.beginTransaction().remove(profile).commit();
			if(townhall != null)
				fm.beginTransaction().remove(townhall).commit();
			if(filter != null)
				fm.beginTransaction().remove(filter).commit();

			fm.beginTransaction().add(
					android.R.id.content, newFilter, "filter").commit();
			break;

		default:
			assert(false);
		}
	}



	public static Twitter getTwitter() {
		return mTwitter;
	}





}