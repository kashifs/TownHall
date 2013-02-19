package com.actionbarsherlock.sample.styled;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.User;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TweetsActivity extends ListActivity {
	private static final String TAG = "TweetsActivity";

	private static ArrayList<JSONObject> jobs;
	private Twitter t;
	private Handler getTweetsHandler, updateTweetsHandler;
	private static String[] tweets, locations;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		t = ((TwitterApplication)getApplication()).getTwitter();

		final ListActivity save = this;
		new GetTweets().execute();


		getTweetsHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				Intent intent = new Intent(save, StyledActivity.class);
				startActivity(intent);
			}
		};
	}


	private class GetTweets extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params){
			jobs = TweetReader.retrieveSpecificUsersTweets(t);
				

			int numJobs = jobs.size();

			tweets = new String[numJobs];
			locations = new String[numJobs];
			
			int[] tweetsToRemove = new int[numJobs];
			int removeIndex = 0;

			for(int i = 0; i < numJobs; i++) {

				try {
					JSONObject object = jobs.get(i);
					tweets[i] = (String)object.get("tweet");
					
					String auth = (String)object.get("author");
					
					if(!auth.contains("Princeton University")){
						tweetsToRemove[removeIndex++] = i;
					}

					User user = (User) object.get("userObj");
					locations[i] = user.getLocation();

				} catch (JSONException e) {
					e.printStackTrace();	
					Log.e(TAG, "We have a JSON exception :(");
				}
				
			}
			
			
			if(removeIndex != 0)
				removeIndex--;
			
//			for(int j = removeIndex; j >= 0; j--)
//				jobs.remove(tweetsToRemove[j]);
			
			return "All Done!";
		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			getTweetsHandler.sendEmptyMessage(0);
		}
	}

	public class UpdateTweets extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params){
			jobs = TweetReader.retrieveSpecificUsersTweets(t);	
			return "All Done!";
		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateTweetsHandler.sendEmptyMessage(0);
		}
	}



	public static String[] getTweets() {
		return tweets;
	}

	public static ArrayList<JSONObject> getJobs() {
		return jobs;
	}

	public static String[] getLocations() {
		return locations;
	}


}