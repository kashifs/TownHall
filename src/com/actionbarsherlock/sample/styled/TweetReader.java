package com.actionbarsherlock.sample.styled;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.location.Location;
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

	private static Location loc = AuthActivity.getLocation();

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




	public static ArrayList<JSONObject> retrieveTweetsAbout(Twitter twitter, String about){
		Query query = new Query(about);
		GeoLocation mLocation = new GeoLocation(loc.getLatitude(), loc.getLongitude());
//		GeoLocation mLocation = new GeoLocation(33.65, -84.42);
		query.geoCode(mLocation, 10, Query.MILES);


		query.setCount(100);
		int numGeoTweets = 0;


		//		Log.d(TAG, "We want tweets related to: " + about);

		QueryResult result = null;
		ArrayList<Status> tweets = null;


			try {			
				result = twitter.search(query);
				tweets = (ArrayList<Status>) result.getTweets();


				JOBS = new ArrayList<JSONObject>();

				int numJobs = tweets.size();

				mTweets = new String[numJobs];
				mLocations = new GeoLocation[numJobs];


				for (int i = 0; i < numJobs; i++) {
					Status t = (Status) tweets.get(i);
					mTweets[i] = t.getText();

					GeoLocation location = t.getGeoLocation();



					if(location != null){
						mLocations[i] = t.getGeoLocation();
						Log.d(TAG, "Found one with a geo location! The tweet is: " + t.getText());
						numGeoTweets++;
						
						Log.d(TAG, "There have been " + numGeoTweets + " geotagged tweets");
					}
					else{
						mLocations[i] = dummyLocations[2];
						Log.d(TAG, "General Location: " + tweets.get(i).getUser().getLocation());
//						continue;
						
						
					}

					JSONObject object = new JSONObject();

					object.put("tweet", t.getText());
					object.put("tweetDate", Utility.getDateDifference(t.getCreatedAt()));
					object.put("author", t.getUser().getName());

					JOBS.add(object);	
				}

			} catch (TwitterException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}


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
