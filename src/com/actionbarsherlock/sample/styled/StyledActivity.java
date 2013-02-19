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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

public class StyledActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
	private static final String TAG = "StyledActivity";


	private final Handler handler = new Handler();

	TabHost mTabHost;
	ViewPager  mViewPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);




		setContentView(R.layout.frag_layout);


		final ActionBar ab = getSupportActionBar();

		// set defaults for logo & home up
		ab.setDisplayHomeAsUpEnabled(true);
		//		ab.setDisplayUseLogoEnabled(useLogo);

		ab.addTab(ab.newTab().setText("Profile").setTabListener(this));
		ab.addTab(ab.newTab().setText("TownHall").setTabListener(this), true);
		ab.addTab(ab.newTab().setText("Filter").setTabListener(this));


		// default to tab navigation
		showTabsNav();

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
			Toast.makeText(getApplicationContext(), "We clicked the icon", Toast.LENGTH_SHORT).show();
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
		ActionBar ab = getSupportActionBar();
		if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
			ab.setDisplayShowTitleEnabled(false);
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		}
	}

	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		Log.i(TAG, "onTabSelected: " + tab.getText());

		String tabName = (String) tab.getText();

		if(tabName.equalsIgnoreCase("profile")) {

		} else if(tabName.equalsIgnoreCase("townhall")) {

		} else if(tabName.equalsIgnoreCase("filter")) {



			//			Intent intent = new Intent(this, DetailsActivity.class);
			//			intent.putExtra("index", 0);
			//			startActivity(intent);

		} else
			Log.e(TAG, "We\'ve reached a place that we shouldn\'t have");

	}

	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		Log.i(TAG, "onTabUnselected: " + tab.getText());
	}

	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		//		Toast.makeText(getApplicationContext(), "onTabReselected: " + tab.getText(), Toast.LENGTH_SHORT).show();
		Log.i(TAG, "onTabReselected: " + tab.getText());

		String tabName = (String) tab.getText();

		if(tabName.equalsIgnoreCase("profile")) {

		} else if(tabName.equalsIgnoreCase("townhall")) {

		} else if(tabName.equalsIgnoreCase("filter")) {

			Intent intent = new Intent(this, DetailsActivity.class);
			intent.putExtra("index", 0);
			startActivity(intent);

		} else
			Log.e(TAG, "We\'ve reached a place that we shouldn\'t have");
	}





}