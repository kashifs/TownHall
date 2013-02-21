package com.actionbarsherlock.sample.styled;

import java.util.ArrayList;

import org.json.JSONObject;

import twitter4j.Twitter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class TweetsActivity extends Activity {
	private static final String TAG = "TweetsActivity";

	public static Handler handleFetchedTweets;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		Twitter t = ((TwitterApplication)getApplication()).getTwitter();

		final Activity save = this;

		TweetReader tweetReader = new TweetReader();

		AsyncTask<Object, Void, ArrayList<JSONObject>> mRetrieveTweets = tweetReader.new AsyncRetrieveTweets();
		mRetrieveTweets.execute(t);


		handleFetchedTweets = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Intent intent = new Intent(save, StyledActivity.class);
				startActivity(intent);
				finish();
			}
		};
	}
	
	public static Handler getTweetsHandler(){
		return handleFetchedTweets;
	}

}
