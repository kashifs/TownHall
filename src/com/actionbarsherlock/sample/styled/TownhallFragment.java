package com.actionbarsherlock.sample.styled;

import java.util.ArrayList;

import org.json.JSONObject;

import twitter4j.Twitter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.sample.styled.TweetReader.AsyncRetrieveTweets;

public class TownhallFragment extends SherlockListFragment {
	boolean isDualPane;
	int mCurCheckPosition = 0;

	public static Handler handleFetchedTweets;

	private static final String TAG = "TownhallFragment";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		Log.i(TAG, "passed through onActivityCreated method of TownhallFragment");

		Twitter t = ((TwitterApplication)getActivity().getApplication()).getTwitter();



		TweetReader tweetReader = new TweetReader();

		AsyncTask<Object, Void, ArrayList<JSONObject>> mRetrieveTweets = tweetReader.new AsyncRetrieveTweets();
		mRetrieveTweets.execute(t);


		handleFetchedTweets = new Handler() {
			@Override
			public void handleMessage(Message msg) {
//				Intent intent = new Intent(save, StyledActivity.class);
//				startActivity(intent);
			}
		};


		setListAdapter(new TwitterListAdapter(getActivity(), TweetReader.getJobs()));


		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = getActivity().findViewById(R.id.details);
		isDualPane = (detailsFrame != null) && (detailsFrame.getVisibility() == View.VISIBLE);

		if (savedInstanceState != null) {
			// Restore last state for checked position.
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}

		if (isDualPane) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);


			showDetails(mCurCheckPosition);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetails(position);
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a
	 * whole new activity in which it is displayed.
	 */
	void showDetails(int index) {
		mCurCheckPosition = index;

		if (isDualPane) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show the data.
			getListView().setItemChecked(index, true);

			// Check what fragment is currently shown, replace if needed.
			DetailsFragment details = (DetailsFragment)
					getActivity().getSupportFragmentManager().findFragmentById(R.id.details);
			if (details == null || details.getShownIndex() != index) {
				// Make new fragment to show this selection.
				details = DetailsFragment.newInstance(index);

				// Execute a transaction, replacing any existing fragment
				// with this one inside the frame.
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.details, details);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}

		} else {
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			Intent intent = new Intent();
			intent.setClass(getActivity(), DetailsActivity.class);
			intent.putExtra("index", index);
			startActivity(intent);
		}
	}
}
