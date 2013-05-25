package ke.co.innova;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract.Constants;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

public class SearchAdapter extends BaseAdapter implements Filterable{

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private ArrayList<HashMap<String, String>> Newdata;
	private ArrayList<String> listpicOrigin;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private Context context;
	// ListFilter  filter ;
	   // List<Item> filteredItems;
	public SearchAdapter(Activity a, ArrayList<HashMap<String, String>> d,
			Context context) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		this.context = context;
		
		Newdata=d;
		//this.listpicOrigin = new ArrayList<String>();
		//this.listpicOrigin.a
		//this.listpicOrigin.addAll(data);
       // this.listpicOrigin.adda
		
	}
	
	public SearchAdapter(Context c) {
		  this.context=c;
				  }


	public int getCount() {
		return Newdata.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row, null);

		// TextView pid = (TextView)vi.findViewById(R.id.title); // title
		TextView name = (TextView) vi.findViewById(R.id.artist); // artist name
		// TextView duration = (TextView)vi.findViewById(R.id.duration); //
		// duration
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
																				// image
		Button play = (Button) vi.findViewById(R.id.play);
		Button watch = (Button) vi.findViewById(R.id.watch);
		Button download = (Button) vi.findViewById(R.id.download);

		vi.setTag(play);

		vi.setFocusable(false);
		// play.setFocusable(false);
		// play.Tag=position;
		// play.Clickable=true;
		// ==

		HashMap<String, String> song = new HashMap<String, String>();
		song = Newdata.get(position);
		String songName = song.get(Search.TAG_SONG);
		String artistName = song.get(Search.TAG_ARTIST);
		String artist_song = songName + " " + artistName;
		// pid.setText(song.get(Search.TAG_PID));
		name.setText(artist_song);
		// duration.setText(song.get(CustomizedListView.KEY_DURATION));
		// imageLoader.DisplayImage(song.get(SubscribedActivity.KEY_THUMB_URL),
		// thumb_image);
		imageLoader.DisplayImage(song.get(Search.TAG_PHOTO), thumb_image);

		final String path_mp3 = song.get(Search.TAG_MP3);
		final String path_mp4 = song.get(Search.TAG_MP4);
		
		download.setOnClickListener(new View.OnClickListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((TextView) AndroidTabAndListView.tabHost.getTabWidget()
						.getChildAt(2).findViewById(android.R.id.title))
						.setText("Downloading");
				AndroidTabAndListView.downloadIntent.putExtra("mp4_url",
						path_mp4);
				AndroidTabAndListView.playSpec
						.setContent(AndroidTabAndListView.downloadIntent);

				AndroidTabAndListView.tabHost.getTabWidget().getChildAt(2)
						.setVisibility(View.VISIBLE);
				AndroidTabAndListView.tabHost.setCurrentTab(2);
				/*
				 * AndroidTabAndListView ant=new AndroidTabAndListView();
				 * ant.startDownloadActivity();
				 */
			}
		});
		watch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AndroidTabAndListView.playVideoIntent.putExtra("mp4_url",
						path_mp4);
				AndroidTabAndListView.playSpec
						.setContent(AndroidTabAndListView.playVideoIntent);

				AndroidTabAndListView.tabHost.getTabWidget().getChildAt(2)
						.setVisibility(View.VISIBLE);
				AndroidTabAndListView.tabHost.setCurrentTab(2);
			}
		});
		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent in=new Intent(context,Play.class);
				 * in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 * in.putExtra("url", path); context.startActivity(in);
				 */
				AndroidTabAndListView.playIntent.putExtra("mp3_url", path_mp3);
				AndroidTabAndListView.playSpec
						.setContent(AndroidTabAndListView.playIntent);
				AndroidTabAndListView.tabHost.getTabWidget().getChildAt(2)
						.setVisibility(View.VISIBLE);

				// AndroidTabAndListView.tabHost.setup();
				AndroidTabAndListView.tabHost.setCurrentTab(2);
			}
		});

		return vi;
	}

	 @Override
	    public Filter getFilter()
	    {
	       return new Filter()
	       {
	            @Override
	            protected FilterResults performFiltering(CharSequence charSequence)
	            {
	                FilterResults results = new FilterResults();

	                //If there's nothing to filter on, return the original data for your list
	                if(charSequence == null || charSequence.length() == 0)
	                {
	                    results.values = data;
	                    results.count = data.size();
	                }
	                else
	                {
	                    ArrayList<HashMap<String,String>> filterResultsData = new ArrayList<HashMap<String,String>>();

	                    for(HashMap<String,String> datas : data)
	                    {
	                        //In this loop, you'll filter through originalData and compare each item to charSequence.
	                        //If you find a match, add it to your new ArrayList
	                        //I'm not sure how you're going to do comparison, so you'll need to fill out this conditional
	                        if(datas.toString().trim().toLowerCase().contains(charSequence.toString().toLowerCase().trim()))
	                        {
	                            filterResultsData.add(datas);
	                        }
	                    }            

	                    results.values = filterResultsData;
	                    results.count = Newdata.size();
	                }

	                return results;
	            }

	            @Override
	            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
	            {
	                Newdata = (ArrayList<HashMap<String,String>>)filterResults.values;
	                notifyDataSetChanged();
	            }
	        };
	    }

	
}


