package com.actionbarsherlock.sample.styled;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailsFragment extends SherlockFragment {
	/**
	 * Create a new instance of DetailsFragment, initialized to
	 * show the text at 'index'.
	 */

//	private static String[] locations;
	private final static String TAG = "DetailsFragment";
	private final static String holder = "a long time ago in a galaxy far, far away....";
	
	public static DetailsFragment newInstance(int index) {
		DetailsFragment f = new DetailsFragment();
//		locations = TweetsActivity.getLocations();
		
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {

			return null;
		}

		ScrollView scroller = new ScrollView(getActivity());
		TextView text = new TextView(getActivity());
		int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
		text.setPadding(padding, padding, padding, padding);
		scroller.addView(text);

		
		int index = getShownIndex();
		
		
		String[] locations = TweetsActivity.getLocations();

		if(locations[index] == null)
			text.setText((index + 1) + ".)" + " This tweet is from " + holder);
		else
			text.setText((index + 1) + ".)" + " This tweet came from " + locations[index]);
		return scroller;
	}
}