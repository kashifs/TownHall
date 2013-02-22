package com.actionbarsherlock.sample.styled;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TwitterListAdapter extends ArrayAdapter<JSONObject> {

	private static final String TAG = "TwitterListAdapter";
	
	private static boolean isRight = true;
	
	private TextView textView;

	public TwitterListAdapter(Activity activity, List<JSONObject> imageAndTexts) {
		super(activity, 0, imageAndTexts);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		// Inflate the views from XML
		View rowView = inflater.inflate(R.layout.image_text_layout, null);
		final JSONObject jsonImageText = getItem(position);



		//////////////////////////////////////////////////////////////////////////////////////////////////////
		//The next section we update at runtime the text - as provided by the JSON from our REST call
		////////////////////////////////////////////////////////////////////////////////////////////////////

		// Set the text on the TextView
		textView = (TextView) rowView.findViewById(R.id.job_text);
		

		if(isRight)
			textView.setBackgroundResource(R.drawable.blue_speech_bubble_right);
		else
			textView.setBackgroundResource(R.drawable.blue_speech_bubble_left);

		isRight = !isRight;

//		Log.d(TAG, jsonImageText.toString());

		try {
			String tweet = (String)jsonImageText.get("tweet");
			String auth = (String)jsonImageText.get("author");
			String date = (String)jsonImageText.get("tweetDate");


			if (date.length() > 0){
				String latest = tweet + "<br><br><i>" + auth + " - " + date + "</i>";
				textView.setText(Html.fromHtml(latest));
			} else {
				textView.setText(Html.fromHtml(tweet) + "\n" + Html.fromHtml(auth));
			}


		} catch (JSONException e) {
			textView.setText("JSON Exception");
		}


		return rowView;
	} 


}