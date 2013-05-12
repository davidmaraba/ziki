package ke.co.innova;

import android.app.ListActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ke.co.innova.InboxActivity.LoadInbox;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class KenyanHits extends Gospel {
	
	private ProgressDialog pDialog;
	
	// songs JSONArray
	JSONArray songs = null;

	// Inbox JSON url
	private static final String INBOX_URL = "http://powerful-hamlet-7423.herokuapp.com/songs/";
	
	// JSON Node names
		private static final String TAG_OBJECTS = "objects";
		private static final String TAG_ARTIST = "artist";
		private static final String TAG_DURATION = "duration";
		private static final String TAG_LABEL = "label";
		private static final String TAG_PHOTO = "photo";
		private static final String TAG_SONG = "song";
		private static final String TAG_ID = "id";

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
		 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_songs);
		
		
        // Loading INBOX in Background Thread
        new LoadInbox().execute();
	}

	/**
	 * Background Async Task to Load all INBOX messages by making HTTP Request
	 * */
	class LoadInbox extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(KenyanHits.this);
			pDialog.setMessage("Loading Songs ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Inbox JSON
		 * */
		protected String doInBackground(String... args) {
			
			try{
				
				URL ziki = new URL("http://powerful-hamlet-7423.herokuapp.com/songs/");
		        URLConnection tc = ziki.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(
		                tc.getInputStream()));
		        String line;
		        while ((line = in.readLine()) != null) {
		        	songs = new JSONArray(line);
		            for (int i = 0; i < songs.length(); i++) {
		                JSONObject c = (JSONObject) songs.get(i);
						
						// Storing each json item in variable
						String id = c.getString(TAG_ID);
						String artist = c.getString(TAG_ARTIST);
						//String duration = c.getString(TAG_DURATION);
						//String label = c.getString(TAG_LABEL);
						String photo = c.getString(TAG_PHOTO);
						String song = c.getString(TAG_SONG);
						
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_PHOTO, photo);
						map.put(TAG_ARTIST, artist);
						//map.put(TAG_DURATION, duration);
						map.put(TAG_SONG, song);
						
						// adding HashList to ArrayList
						songsList.add(map);
					}
		        }

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							KenyanHits.this, songsList,
							R.layout.songs_lists, new String[] { TAG_SONG, TAG_ARTIST, TAG_PHOTO  },
							new int[] { R.id.from, R.id.subject, R.id.date });
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}



