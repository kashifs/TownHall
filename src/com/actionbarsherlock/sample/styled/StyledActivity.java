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

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;
import android.widget.TabHost;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.nineoldandroids.animation.ObjectAnimator;

public class StyledActivity extends SherlockFragmentActivity implements ActionBar.TabListener {

	private final Handler handler = new Handler();
	private RoundedColourFragmentList leftFrag, rightFrag;
	private boolean useLogo = false;
	private boolean showHomeUp = false;

	TabHost mTabHost;
	ViewPager  mViewPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
		
        setContentView(R.layout.frag_layout);



		final ActionBar ab = getSupportActionBar();

		// set defaults for logo & home up
		ab.setDisplayHomeAsUpEnabled(showHomeUp);
		ab.setDisplayUseLogoEnabled(useLogo);

		// set up tabs nav
		//        for (int i = 1; i < 4; i++) {
		//            ab.addTab(ab.newTab().setText("Tab " + i).setTabListener(this));
		//        }

		ab.addTab(ab.newTab().setText("Profile").setTabListener(this));
		ab.addTab(ab.newTab().setText("TownHall").setTabListener(this), true);
		ab.addTab(ab.newTab().setText("Filter").setTabListener(this));


		//		mTabsAdapter.addTab(mTabHost.newTabSpec("profile").setIndicator("Profile"),
		//                LoaderCustomSupport.AppListFragment.class, null);


		// set up list nav
		ab.setListNavigationCallbacks(ArrayAdapter
				.createFromResource(this, R.array.sections,
						R.layout.sherlock_spinner_dropdown_item),
						new OnNavigationListener() {
			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {
				// FIXME add proper implementation
				//				rotateLeftFrag();
				return false;
			}
		});

		// default to tab navigation
		showTabsNav();

		// create a couple of simple fragments as placeholders
		final int MARGIN = 16;
		leftFrag = new RoundedColourFragmentList(getResources().getColor(
				R.color.android_green), 1f, MARGIN, MARGIN / 2, MARGIN, MARGIN);
		rightFrag = new RoundedColourFragmentList(getResources().getColor(
				R.color.honeycombish_blue), 2f, MARGIN / 2, MARGIN, MARGIN,
				MARGIN);
		





		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//		ft.add(R.id.root, leftFrag);
//		ft.add(R.id.root, rightFrag);
		ft.commit();
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
			// TODO handle clicking the app icon/logo
			return false;
		case R.id.menu_refresh:
			// switch to a progress animation
			item.setActionView(R.layout.indeterminate_progress_action);
			return true;
		case R.id.menu_nav_tabs:
			item.setChecked(true);
			showTabsNav();
			return true;
		case R.id.menu_nav_label:
			item.setChecked(true);
			showStandardNav();
			return true;
		case R.id.menu_nav_drop_down:
			item.setChecked(true);
			showDropDownNav();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void rotateLeftFrag() {
		if (leftFrag != null) {
			ObjectAnimator.ofFloat(leftFrag.getView(), "rotationY", 0, 180)
			.setDuration(500).start();
		}
	}

	private void showStandardNav() {
		ActionBar ab = getSupportActionBar();
		if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_STANDARD) {
			ab.setDisplayShowTitleEnabled(true);
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		}
	}

	private void showDropDownNav() {
		ActionBar ab = getSupportActionBar();
		if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_LIST) {
			ab.setDisplayShowTitleEnabled(false);
			ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
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
		// FIXME add a proper implementation, for now just rotate the left
		// fragment
		//		rotateLeftFrag();
	}

	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// FIXME implement this
	}

	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// FIXME implement this
	}





}