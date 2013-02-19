package com.actionbarsherlock.sample.styled;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class TweetReader {
	private static final String TAG = "TweetReader";

	private static ArrayList<JSONObject> jobs;
	private static String[] tweets, locations;


	/*
	 * The first parameter is the tweet, the second is an array used as a filter
	 */
	public class AsyncRetrieveTweets extends AsyncTask<Object, Void, ArrayList<JSONObject>> {

		@Override
		protected ArrayList<JSONObject> doInBackground(Object... params) {

			Twitter twitter = (Twitter)params[0];
			List<twitter4j.Status> statuses = retrieveSpecificUsersTweets(twitter);
			jobs = convertTimelineToJson(statuses);

			int numJobs = jobs.size();


			tweets = new String[numJobs];
			locations = new String[numJobs];


			for(int i = 0; i < numJobs; i++) {

				try {
					JSONObject object = jobs.get(i);

					tweets[i] = (String)object.get("tweet");
					locations[i] = ((User) object.get("userObj")).getLocation();

				} catch (JSONException e) {
					e.printStackTrace();	
					Log.e(TAG, "We have a JSON exception :(");
				}
			}


			return jobs;
		}

		@Override
		protected void onPostExecute(ArrayList<JSONObject> result) {
			super.onPostExecute(result);
			Handler tweetHandler = TweetsActivity.getTweetsHandler();
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



	/**
	 * Method that converts a list of Status' to a JSON array that can be displayed by the grid view
	 * @param statuses
	 * @return
	 */
	private static ArrayList<JSONObject> convertTimelineToJson(List<Status> statuses) {
		ArrayList<JSONObject> JOBS = new ArrayList<JSONObject>();
		try {
			if (statuses.size()>0){
				for (Status s : statuses){
					String avatar = "http://" + s.getUser().getProfileImageURL().getHost() + s.getUser().getProfileImageURL().getPath();
					JSONObject object = new JSONObject();

					object.put("tweet", s.getText());
					object.put("tweetDate", Utility.getDateDifference(s.getCreatedAt()));
					object.put("author", s.getUser().getName());
					object.put("avatar", avatar);
					object.put("userObj", s.getUser());
					object.put("tweetId", s.getId());

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
		return tweets;
	}

	public static ArrayList<JSONObject> getJobs() {
		return jobs;
	}

	public static String[] getLocations() {
		return locations;
	}
}
