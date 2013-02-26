package com.actionbarsherlock.sample.styled;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.actionbarsherlock.app.SherlockFragment;


public class ProfileFragment extends SherlockFragment {
	/**
	 * Create a new instance of ProfileFragment, initialized to
	 * show the text at 'index'.
	 */

	private final static String TAG = "DetailsFragment";
	private final static String holder = "a long time ago in a galaxy far, far away....";

	public static ProfileFragment newInstance(int index) {


		ProfileFragment f = new ProfileFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
		//		return 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		ScrollView scroller = new ScrollView(getActivity());


		LinearLayout ll = new LinearLayout(getActivity());
		ll.setOrientation(LinearLayout.VERTICAL);


//		ImageView profileImage = new ImageView(getActivity());
//		profileImage.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.icon));
		
		Button postStat = new Button(getActivity());
		postStat.setText("Post New Status");
		
		
		
		OnClickListener postListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                
            	Intent intent = new Intent();
    			intent.setClass(getActivity(), PostStatusActivity.class);
    			

    			
    			startActivity(intent);
            }
        };
        
        postStat.setOnClickListener(postListener);

		Button sendMessage = new Button(getActivity());
		sendMessage.setText("Send Private Message");

		Button followUser = new Button(getActivity());
		followUser.setText("Follow User");
		



//		ll.addView(profileImage);
		ll.addView(postStat);
		ll.addView(sendMessage);
		ll.addView(followUser);

		scroller.addView(ll);

		return scroller;
	}
}