package ke.co.innova;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyHits extends ListActivity{

	
	private ProgressDialog pDialog;
	
	// songs JSONArray
	JSONArray songs = null;

	// Inbox JSON url
		
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
	
	public static final String media_url = "http://radiant-forest-1098.herokuapp.com";
	public static final String photo_url = "http://radiant-forest-1098.herokuapp.com";

	public static final String KEY_THUMB_URL="artist_image";
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
		 
    public LazyAdapter  adapter;
    
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kenyan_hits);
		
		//jsonParser=new JSONParser();
		
		
		//subscriptionList=new ArrayList<HashMap<String,String>>();
		
		//session=new SessionManager(getApplicationContext());
		
        new LoadSubscriptions().execute();
        	
	}
    
    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
 
    }
    class LoadSubscriptions extends AsyncTask<String, String, String> {
    	 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyHits.this);
            pDialog.setMessage("Loading the songs. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * getting All products from url
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
                	for(int i=0;i<=songsList.size();i++)
                	{
                		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                				LinearLayout.LayoutParams.WRAP_CONTENT);
                
                	}
                   
            		//list=(ListView)findViewById(R.id.list);
            		
            		// Getting adapter by passing xml data ArrayList
                //  adapter=new LazyAdapter(new MainActivity(),subscriptionList);
                  
                    adapter=new LazyAdapter(MyHits.this, songsList,getApplicationContext());        
                    
                    // updating listview
                    setListAdapter(adapter);
                }
            });
 
        }
 
    }
    
    
}
