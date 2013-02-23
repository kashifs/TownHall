package com.actionbarsherlock.sample.styled;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class TweetReader {
	private static final String TAG = "TweetReader";

	private static ArrayList<JSONObject> JOBS;
	private static String[] mTweets;
	private static GeoLocation[] mLocations;
	
	private static GeoLocation philly = new GeoLocation(39.9522, -75.1642);
	private static GeoLocation newyork = new GeoLocation(40.77, -73.98);
	private static GeoLocation atlanta = new GeoLocation(33.65, -84.42);
	private static GeoLocation maui = new GeoLocation(20.97, -156.83);
	private static GeoLocation losangeles = new GeoLocation( 33.93, -118.40);
	
//	static final LatLng PHILADELPHIA = new LatLng(39.9522, -75.1642);
	
	private static GeoLocation[] dummyLocations = {philly, newyork, atlanta, maui, losangeles};
	


	/*
	 * The first parameter is the tweet, the second is a string used as a filter
	 */
	public class AsyncRetrieveTweets extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... params) {
			
			Twitter twitter = (Twitter)params[0];
			String about = (String)params[1];

			retrieveTweetsAbout(twitter, about);

			return null;
		}

		@Override
		protected void onPostExecute(Void word) {
			Handler tweetHandler = TownhallFragment.getTweetsHandler();
			tweetHandler.sendEmptyMessage(0);
		}
	}

	/**
	 * a method to retrive a list of tweets from the users who the current user is following
	 * @return List JSON of other tweets
	 */

	public static List<Status> retrieveSpecificUsersTweets(Twitter twitter){
		List<Status> statuses = new ArrayList<Status>();
		Paging p = new Paging(1);

		try {

			statuses = twitter.getFriendsTimeline(p);

		} catch (TwitterException e) {
			Log.e("Twitter", "Error retrieving tweets");
			Log.e("Twitter", e.getMessage());
		}

		return statuses;
	}


	public static ArrayList<JSONObject> retrieveTweetsAbout(Twitter twitter, String about){
		Query query = new Query(about);
		query.setRpp(20);


		Log.d(TAG, "We want tweets related to: " + about);
		
		QueryResult result = null;
		ArrayList<Tweet> tweets = null;



		try {
			result = twitter.search(query);
			tweets = (ArrayList<Tweet>) result.getTweets();
			JOBS = new ArrayList<JSONObject>();

			int numJobs = tweets.size();

			mTweets = new String[numJobs];
			mLocations = new GeoLocation[numJobs];

			for (int i = 0; i < numJobs; i++) {
				Tweet t = (Tweet) tweets.get(i);
				mTweets[i] = t.getText();
				GeoLocation location = t.getGeoLocation();
				
				if(location != null){
					mLocations[i] = t.getGeoLocation();
					Log.d(TAG, "Found one with a location!");
				}
				else{
					int rand  = (int)(Math.random()*(4));
							
					mLocations[i] = dummyLocations[rand];
//					Log.d(TAG, "The random one we chose is:" + rand);
					Log.d(TAG, "This has no location :(");
				}

				JSONObject object = new JSONObject();

				object.put("tweet", t.getText());
				object.put("tweetDate", Utility.getDateDifference(t.getCreatedAt()));
				object.put("author", t.getFromUser());

				JOBS.add(object);	
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return JOBS;
	}





	public static String[] getTweets() {
		return mTweets;
	}

	public static ArrayList<JSONObject> getJobs() {
		return JOBS;
	}

	public static GeoLocation[] getLocations() {
		return mLocations;
	}
}
