package ke.co.innova;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ke.co.innova.MyHits.LoadSubscriptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class Hits extends Activity {

    ProgressDialog pDialog;
    private static String url_all_products = "http://192.168.203.54/bizlink/get_subscriptions.php?kim@gmail.com";
    private String email="kim@gmail.com";
	JSONParser jParser=new JSONParser();
	private static final String TAG_OBJECTS = "objects";
	public static final String TAG_ARTIST = "artist";
	private static final String TAG_DURATION = "duration";
	public static final String TAG_LABEL = "label";
	public static final String TAG_PHOTO = "photo";
	public static final String TAG_SONG = "song";
	public static final String TAG_ID = "id";
	public static final String KEY_THUMB_URL="image_url";
	JSONArray songs = null;
	ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    
    private JSONParser jsonParser;
    //SessionManager session;
    SharedPreferences pref;
    
    LazyAdapter adapter;
    ListView list;
    ExpandableListView expandbleLis;
    
    Context context;
    JSONArray subscriptions=null;
    
    public Hits()
    {
    	
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.all_ngomaz);
		 new LoadSubscriptions().execute();
	        
	}

	/*public void setGroupData() {
		
		/*  HashMap<String, String> song = new HashMap<String, String>();
		  
		for(int i=0;i<songsList.size();i++)
		{
			song = songsList.get(i);
			groupItem.add(song.get(TAG_ARTIST));
			//groupItem.add(song.get(TAG_ID));
		}*/
		/*groupItem.add("Top 10 Kenyan Hits");
		groupItem.add("Top 10 Gospel Hits");
		groupItem.add("Top 10 Ziki Hits");
		groupItem.add("Top 10 New Releases");
	}*/

	ArrayList<Parent> groupItem = new ArrayList<Parent>();
	ArrayList<String> childItem = new ArrayList<String>();

	public void setChildGroupData() {
		  for (int i = 0; i < 2; i++){
	            //for each "i" create a new Parent object to set the title and the children
	            Parent parent = new Parent();
	            parent.setTitle("Parent " + i);

	          //  arrayChil = new ArrayList<String>();
	            for (int j = 0; j<3; j++) {
	                childItem.add("Child " + j);
	            }
	            parent.setArrayChildren(childItem);

	            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
	            groupItem.add(parent);
	        }
		/**
		 * Add Data For TecthNology
		 */
		/*ArrayList<String> child = new ArrayList<String>();
		child.add("Java");
		child.add("Drupal");
		child.add(".Net Framework");
		child.add("PHP");
		childItem.add(child);

		/**
		 * Add Data For Mobile
		 */
		/*child = new ArrayList<String>();
		child.add("Android");
		child.add("Window Mobile");
		child.add("iPHone");
		child.add("Blackberry");
		childItem.add(child);
		/**
		 * Add Data For Manufacture
		 */
		/*child = new ArrayList<String>();
		child.add("HTC");
		child.add("Apple");
		child.add("Samsung");
		child.add("Nokia");
		childItem.add(child);
		/**
		 * Add Data For Extras
		 */
		/*child = new ArrayList<String>();
		child.add("Contact Us");
		child.add("About Us");
		child.add("Location");
		child.add("Root Cause");
		childItem.add(child);*/
	}

	
public class LoadSubscriptions extends AsyncTask<String, String, String>
{

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(String result) {
		// dismiss the dialog after getting all products
					pDialog.dismiss();
					// updating UI from Background Thread
					runOnUiThread(new Runnable() {
						public void run() {
							

							//setGroupData();
							setChildGroupData();

							 expandbleLis.setAdapter(new MyCustomAdapter(Hits.this,groupItem));
							 //expandbleLis.setOnChildClickListener((OnChildClickListener) Hits.this);
							 
							 expandbleLis.setOnGroupClickListener(new OnGroupClickListener() {
								
								@Override
								public boolean onGroupClick(ExpandableListView parent, View v,
										int groupPosition, long id) {
									expandbleLis.expandGroup(groupPosition);
									// TODO Auto-generated method stub
									return true;
								}
							});
						       //expandbleLis.expandGroup(0);
						      // expandbleLis.expandGroup(1);
							//NewAdapter mNewAdapter=new Adapter();
							//mNewAdapter.setArrayChildren(childItem);
							
							//NewAdapter mNewAdapter = new NewAdapter(songsList, childItem);
														
							/*mNewAdapter
									.setInflater(
										(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
											Hits.this);
							getExpandableListView().setAdapter(mNewAdapter);
						expandbleLis.setOnChildClickListener(Hits.this);*/
						
						}
					});
					
					
					expandbleLis.setOnGroupExpandListener(new OnGroupExpandListener() {
						int previousGroup=-1;
						@Override
						public void onGroupExpand(int groupPosition) {
							// TODO Auto-generated method stub
							if(groupPosition != previousGroup)
				                expandbleLis.collapseGroup(previousGroup);
				            previousGroup = groupPosition;
						}
					});
	}

	@Override
	protected void onPreExecute() {
		 super.onPreExecute();
		 expandbleLis=(ExpandableListView)findViewById(R.id.expandable_list);
		pDialog = new ProgressDialog(Hits.this);
        pDialog.setMessage("Loading the Hits. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		

          try {
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
						String photo = "ziki.com/" + c.getString(TAG_PHOTO);
						String song = c.getString(TAG_SONG);
						String imgurl="http://";
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_PHOTO, photo);
						map.put(TAG_ARTIST, artist);
						//map.put(TAG_DURATION, duration);
						map.put(TAG_SONG, song);
						map.put(KEY_THUMB_URL,imgurl);
						
						// adding HashList to ArrayList
						songsList.add(map);
					}
              } 
          } catch (JSONException e) {
              e.printStackTrace();
          }
          catch (Exception e) {
			// TODO: handle exception
		}

          return null;
	}
	

	
}
	
}
