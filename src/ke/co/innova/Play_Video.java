package ke.co.innova;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class Play_Video extends Activity {
	// private String path = "http://192.168.203.54/bangoh/my.mp4";
	// private String path="http://192.168.203.54/bangoh/.mp3";
	private VideoView video_view_;

	Intent intent;
	String path;// =intent.getExtras().getString("mp4_url");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_video);

		video_view_ = (VideoView) findViewById(R.id.surface_view);

		intent = getIntent();
		path = intent.getExtras().getString("mp4_url");

		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		video_view_.setVideoURI(Uri.parse(path));
		video_view_.setMediaController(new MediaController(this));
		video_view_.requestFocus();
		video_view_.start();
	}
}
