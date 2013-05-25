package ke.co.innova;

import android.R.anim;
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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class Gospel extends ListActivity {

	private ProgressDialog pDialog;

	// songs JSONArray
	JSONArray songs = null;

	// Inbox JSON url
	private static final String INBOX_URL = "http://radiant-forest-1098.herokuapp.com/gospelapi/";

	// JSON Node names
	public static final String TAG_OBJECTS = "objects";
	public static final String TAG_ARTIST = "artist_name";
	public static final String TAG_DURATION = "duration";
	public static final String TAG_LABEL = "record_label";
	public static final String TAG_PHOTO = "photo";
	public static final String TAG_SONG = "song";
	public static final String TAG_ID = "id";
	public static final String TAG_MP3="mp3_music_file";
	public static final String TAG_MP4="mp4_music_file";
	public static final String TAG_CHART_RANK="charts_rank";
	CheckInternetConnectivity checkInternetConnectivty;
	private GospelAdapter adapter;
	public static final String media_url = "http://radiant-forest-1098.herokuapp.com";
	public static final String photo_url = "http://radiant-forest-1098.herokuapp.com";
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inbox_list);
		// Loading INBOX in Background Thread
		new LoadInbox().execute();

		checkInternetConnectivty=new CheckInternetConnectivity(getApplicationContext());
		checkInternetConnectivty.checkConnectivity();
		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				

				if (CheckInternetConnectivity.isOnline) {
					((TextView) AndroidTabAndListView.tabHost.getTabWidget()
							.getChildAt(1).findViewById(android.R.id.title))
							.setText("Kenyan Hits");

					// AndroidTabAndListView.hitsSpec.setIndicator("Kenyan Hits");
					AndroidTabAndListView.tabHost.getTabWidget().getChildAt(1)
							.setVisibility(View.VISIBLE);
					// AndroidTabAndListView.tabHost.setup();
					AndroidTabAndListView.tabHost.setCurrentTab(1);
					// AndroidTabAndListView.tabHost.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE);
				} else {
					showDialog();

				}
			}
		});
		final Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (CheckInternetConnectivity.isOnline)
				{
				// Perform action on click
				// startActivity (new Intent(getApplicationContext(),
				// Help.class));
				// Intent gospelIntent=new Intent(this,Gospel_Hits.class);
				((TextView) AndroidTabAndListView.tabHost.getTabWidget()
						.getChildAt(1).findViewById(android.R.id.title))
						.setText("Gospel Hits");

				// AndroidTabAndListView.hitsSpec.setIndicator("Gospel Hits");
				AndroidTabAndListView.hitsSpec
						.setContent(AndroidTabAndListView.gospelIntent);

				AndroidTabAndListView.tabHost.getTabWidget().getChildAt(1)
						.setVisibility(View.VISIBLE);
				AndroidTabAndListView.tabHost.setCurrentTab(1);
				}
				else
				{
					showDialog();
				}
			}
		});
		final Button button3 = (Button) findViewById(R.id.btntopZikiHits);
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (CheckInternetConnectivity.isOnline)
				{
				// Perform action on click
				// AndroidTabAndListView.hitsSpec.setIndicator("Top Ziki Hits");
				((TextView) AndroidTabAndListView.tabHost.getTabWidget()
						.getChildAt(1).findViewById(android.R.id.title))
						.setText("Top Ziki Hits");

				AndroidTabAndListView.hitsSpec
						.setContent(AndroidTabAndListView.zikiHitsIntent);

				AndroidTabAndListView.tabHost.getTabWidget().getChildAt(1)
						.setVisibility(View.VISIBLE);
				AndroidTabAndListView.tabHost.setCurrentTab(1);
				}
				else
				{
					showDialog();
				}
			}
		});
		final Button button4 = (Button) findViewById(R.id.btntopNewReleases);
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (CheckInternetConnectivity.isOnline)
				{
				// Perform action on click
				// AndroidTabAndListView.hitsSpec.setIndicator("New Releases");
				((TextView) AndroidTabAndListView.tabHost.getTabWidget()
						.getChildAt(1).findViewById(android.R.id.title))
						.setText("New Releases");

				AndroidTabAndListView.hitsSpec
						.setContent(AndroidTabAndListView.newReleasesInten);

				AndroidTabAndListView.tabHost.getTabWidget().getChildAt(1)
						.setVisibility(View.VISIBLE);
				AndroidTabAndListView.tabHost.setCurrentTab(1);
				}
				else
				{
					showDialog();
				}
			}
		});

	}
	public void showDialog()
	{
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);//.create();
		alertDialog.setTitle("No Network Connection");
		alertDialog.setMessage("Please connect to a network and try again");
		
		alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				// TODO Auto-generated method stub

			}
		});
		alertDialog.setNegativeButton("Cancel",null);/*new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				alertDialog.
			}
		});*/
		alertDialog.setIcon(R.drawable.about);
		alertDialog.show();
	}

	private void addTab() {
		Intent newRoom = new Intent();

		newRoom.setClass(this, MyHits.class);

		AndroidTabAndListView.tabHost.addTab(AndroidTabAndListView.tabHost
				.newTabSpec("NewRoomTab").setIndicator("New room")
				.setContent(newRoom));
		// AndroidTabAndListView.tabHost.setCurrentTab(index)
		int pos = AndroidTabAndListView.tabHost.getCurrentTab();
		AndroidTabAndListView.tabHost.setCurrentTab(pos);

		// Log.d("z", Integer.toString(z));

		// ++z;
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
			pDialog = new ProgressDialog(Gospel.this);
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
				
				URL ziki = new URL("http://radiant-forest-1098.herokuapp.com/gospelapi/");
		        URLConnection tc = ziki.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(
		                tc.getInputStream()));
		        String line;
		        //Log.d("JSON",line);
		        while ((line = in.readLine()) != null) {
		        	//line = in.readLine();
		        	songs = new JSONArray(line);
		            for (int i = 0; i < songs.length(); i++) {
		                JSONObject c = (JSONObject) songs.get(i);
						
						// Storing each json item in variable
						String id = c.getString(TAG_ID);
						//String artist = c.getString(TAG_ARTIST);
						String artist=c.getString(TAG_ARTIST);
						String duration = c.getString(TAG_DURATION);
						String chartRank=c.getString(TAG_CHART_RANK);
						//String label = c.getString(TAG_LABEL);
						String photo = media_url + c.getString(TAG_PHOTO);
						String song = c.getString(TAG_SONG);
						String label=c.getString(TAG_LABEL);
						
						String mp3= media_url + c.getString(TAG_MP3);
						String mp4= media_url + c.getString(TAG_MP4);
						Log.d("mp4", mp4);
						//String img="http://";
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
                    

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_PHOTO, photo);
						map.put(TAG_ARTIST, artist);
						//map.put(TAG_DURATION, duration);
						map.put(TAG_CHART_RANK, chartRank);
						map.put(TAG_SONG, song);
						map.put(TAG_LABEL, label);
						map.put(TAG_MP3, mp3);
						map.put(TAG_MP4,mp4);
						
						// adding HashList to ArrayList
						songsList.add(map);
					}
		        }
            } catch (JSONException e) {
                e.printStackTrace();
            }
catch (Exception e) {
	e.printStackTrace();
	// TODO: handle exception
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
					/*ListAdapter adapter = new SimpleAdapter(Gospel.this,
							songsList, R.layout.all_songs, new String[] {
									TAG_SONG, TAG_ARTIST, TAG_PHOTO },
							new int[] { R.id.from, R.id.subject, R.id.date });
					// updating listview
					setListAdapter(adapter);*/
					 adapter=new GospelAdapter(Gospel.this, songsList,getApplicationContext());        
	                    
	                    // updating listview
	                    setListAdapter(adapter);
				}
			});

		}

	}
}
