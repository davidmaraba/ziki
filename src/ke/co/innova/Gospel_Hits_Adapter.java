package ke.co.innova;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

public class Gospel_Hits_Adapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private Context context;

	public Gospel_Hits_Adapter(Activity a,
			ArrayList<HashMap<String, String>> d, Context context) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
		this.context = context;
	}

	public Gospel_Hits_Adapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return data.size();
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
			vi = inflater.inflate(R.layout.layout_list, null);

		// TextView pid = (TextView)vi.findViewById(R.id.title); // title
		TextView name = (TextView) vi.findViewById(R.id.artist); // artist name
		// TextView duration = (TextView)vi.findViewById(R.id.duration); //
		// duration
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
																				// image
		final Button play = (Button) vi.findViewById(R.id.playes);
		Button watch = (Button) vi.findViewById(R.id.watch);
		Button download = (Button) vi.findViewById(R.id.download);

		vi.setTag(play);

		vi.setFocusable(false);
		// play.setFocusable(false);
		// play.Tag=position;
		// play.Clickable=true;
		// ==

		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);
		String songName = song.get(Gospel_Hits.TAG_SONG);
		String artistName = song.get(Gospel_Hits.TAG_ARTIST);
		String artist_song = songName + " " + artistName;
		// pid.setText(song.get(MyHits.TAG_PID));
		name.setText(artist_song);
		// duration.setText(song.get(CustomizedListView.KEY_DURATION));
		// imageLoader.DisplayImage(song.get(SubscribedActivity.KEY_THUMB_URL),
		// thumb_image);
		imageLoader.DisplayImage(song.get(Gospel_Hits.TAG_PHOTO), thumb_image);

		final String path_mp3 = song.get(Gospel_Hits.TAG_MP3);
		final String path_mp4 = song.get(Gospel_Hits.TAG_MP4);
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
				AndroidTabAndListView.playVideoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
				
				if(play.isPressed())
				{
					AndroidTabAndListView.playingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}
				
				AndroidTabAndListView.playingIntent.putExtra("mp3_url", path_mp3);
				

				AndroidTabAndListView.playSpec
						.setContent(AndroidTabAndListView.playingIntent);

				AndroidTabAndListView.tabHost.getTabWidget().getChildAt(2)
						.setVisibility(View.VISIBLE);
				

				// AndroidTabAndListView.tabHost.setup();
				AndroidTabAndListView.tabHost.setCurrentTab(2);
			}
		});

		return vi;
	}
}
