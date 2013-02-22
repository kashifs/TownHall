package com.actionbarsherlock.sample.styled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class TweetReader {
	private static final String TAG = "TweetReader";

	private static ArrayList<JSONObject> JOBS = new ArrayList<JSONObject>();
	private static String[] mTweets, mLocations;


	/*
	 * The first parameter is the tweet, the second is an array used as a filter
	 */
	public class AsyncRetrieveTweets extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... params) {

			Twitter twitter = (Twitter)params[0];
			//			List<twitter4j.Status> statuses = retrieveSpecificUsersTweets(twitter);
			//			convertTimelineToJson(statuses);


			retrieveTweetsAbout(twitter, "sports");


			return null;
		}

		@Override
		protected void onPostExecute(Void word) {
			Handler tweetHandler = AuthActivity.getTweetsHandler();
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

		QueryResult result = null;
		ArrayList<Tweet> tweets = null;




		try {
			result = twitter.search(query);
			tweets = (ArrayList<Tweet>) result.getTweets();

			int numJobs = tweets.size();

			mTweets = new String[numJobs];
			mLocations = new String[numJobs];

			for (int i = 0; i < numJobs; i++) {
				Tweet t = (Tweet) tweets.get(i);
				mTweets[i] = t.getText();
				GeoLocation location = t.getGeoLocation();
				if(location != null)
					mLocations[i] = t.getGeoLocation().toString();
				else
					mLocations[i] = "a long time ago in a galaxy far, far away....";

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





	/**
	 * Method that converts a list of Status' to a JSON array that can be displayed by the grid view
	 * @param statuses
	 * @return
	 */
	private static ArrayList<JSONObject> convertTimelineToJson(List<Status> statuses) {

		int numJobs = statuses.size();

		mTweets = new String[numJobs];
		mLocations = new String[numJobs];
		int i = 0;

		try {
			if (statuses.size() > 0) {
				for (Status s : statuses) {
					JSONObject object = new JSONObject();

					mTweets[i]       = s.getText();
					mLocations[i]  = s.getUser().getLocation();
					i++;

					object.put("tweet", s.getText());
					object.put("tweetDate", Utility.getDateDifference(s.getCreatedAt()));
					object.put("author", s.getUser().getName());


					JOBS.add(object);	
				}
			}else{
				JSONObject object = new JSONObject();
				object.put("tweet", "You have not logged in yet! Please log on to view latest tweets");
				object.put("author", "");
				JOBS.add(object);
			}

		} catch (JSONException e1) {
			Log.e("JSON", "There was an error creating the JSONObject", e1);
		}

		return JOBS;
	}

	public static String[] getTweets() {
		return mTweets;
	}

	public static ArrayList<JSONObject> getJobs() {
		return JOBS;
	}

	public static String[] getLocations() {
		return mLocations;
	}
}
