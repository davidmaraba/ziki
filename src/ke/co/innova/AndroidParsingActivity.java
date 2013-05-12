package ke.co.innova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidParsingActivity extends ListActivity {

	// url to make request
	private static String url = "http://powerful-hamlet-7423.herokuapp.com/songs/";
	//http://powerful-hamlet-7423.herokuapp.com/api/v1/mymodel/
	
	// JSON Node names
	private static final String TAG_OBJECTS = "objects";
	private static final String TAG_ARTIST = "artist";
	private static final String TAG_DURATION = "duration";
	private static final String TAG_LABEL = "label";
	private static final String TAG_PHOTO = "photo";
	private static final String TAG_SONG = "song";
	private static final String TAG_ID = "id";

	// songs JSONArray
	JSONArray songs = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();
		
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
					String duration = c.getString(TAG_DURATION);
					String label = c.getString(TAG_LABEL);
					String photo = c.getString(TAG_PHOTO);
					String song = c.getString(TAG_SONG);
					
					
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					
					// adding each child node to HashMap key => value
					map.put(TAG_ID, id);
					map.put(TAG_PHOTO, photo);
					map.put(TAG_ARTIST, artist);
					map.put(TAG_DURATION, duration);
					map.put(TAG_SONG, song);
	
					// adding HashList to ArrayList
					contactList.add(map);
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
		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.list_item,
				new String[] { TAG_SONG, TAG_ARTIST, TAG_DURATION }, new int[] {
						R.id.name, R.id.email });
		//, R.id.mobile

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
				//String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(TAG_SONG, name);
				in.putExtra(TAG_ARTIST, cost);
				//in.putExtra(TAG_DURATION, description);
				startActivity(in);

			}
		});
		
		

	}

}