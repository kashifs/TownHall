package com.actionbarsherlock.sample.styled;


import twitter4j.AccountSettings;
import twitter4j.Twitter;
import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PostStatusActivity extends Activity {
	private static final String TAG = "PostStatusActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_post_status);


		Button tweetButton = (Button)findViewById(R.id.tweetButton);
		final EditText tweetText = (EditText)findViewById(R.id.editText1);

		//		final PostStatusActivity thisActivity = this;

		OnClickListener tweetListener = new OnClickListener() {
			@Override
			public void onClick(View v) {

				String userTweet = tweetText.getText().toString();
				
				Log.d(TAG, "The tweet is: " + userTweet);
				
				if(userTweet.length() > 0 && userTweet.length() <= 140 ) {

					new AsyncPostTweet().execute(tweetText.getText().toString());
					Toast.makeText(getApplicationContext(),
							"Your tweet has been posted.", Toast.LENGTH_LONG).show();
					finish();
				}


			}
		};



		tweetButton.setOnClickListener(tweetListener);






	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}



	/*
	 * The first parameter is the tweet, the second is a string used as a filter
	 */
	public class AsyncPostTweet extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {

			Twitter mTwitter = StyledActivity.getTwitter();
			try {
				Log.d(TAG, mTwitter.getAccountSettings().toString());

				AccountSettings settings = mTwitter.getAccountSettings();
				
				mTwitter.updateStatus(params[0]);

			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void word) {

		}
	}

} 
