package ke.co.innova;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Play extends Activity implements OnSeekBarChangeListener {

	Intent serviceIntent;
	Button btnPlayStop;
	boolean isMusicPlaying = false;

	private boolean isOnline;

	boolean mBufferingBroadcastIsRegisgtered;
	private ProgressDialog pdBuff = null;

	// seek bar variables
	private SeekBar seekBar;
	private int seekMax;
	private static int songEnded = 0;
	public static String BROADCAST_SEEKBAR = "com.finalplayer.sendSeekBar";
	Intent intent;
	boolean mBroadcastIsRegistered;
	Intent intents;
	String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

		intents = getIntent();
		path = intents.getExtras().getString("mp3_url");
		try {
			serviceIntent = new Intent(this, MyPlayService.class);
			intent = new Intent(BROADCAST_SEEKBAR);
			initViews();
			setListeners();
		} catch (Exception e) {
			e.printStackTrace();
			// e.getClass().getName()+" "+e.getMessage()
			// TODO: handle exception
		}
		btnPlayStop.setBackgroundResource(R.drawable.pause);
		playAudio();
	}

	// updating position of seek bar
	private BroadcastReceiver broadCastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent serviceIntent) {
			// TODO Auto-generated method stub
			updateUI(serviceIntent);
		}
	};

	private void initViews() {
		// TODO Auto-generated method stub
		btnPlayStop = (Button) findViewById(R.id.play_pause_button);
		btnPlayStop.setBackgroundResource(R.drawable.play);
		seekBar = (SeekBar) findViewById(R.id.seekBar01);
	}

	protected void updateUI(Intent serviceIntent) {
		// TODO Auto-generated method stub
		String counter = serviceIntent.getStringExtra("counter");
		String mediaMax = serviceIntent.getStringExtra("mediamax");
		String strSongEnded = serviceIntent.getStringExtra("song_ended");
		int seekProgress = Integer.parseInt(counter);
		seekMax = Integer.parseInt(mediaMax);
		songEnded = Integer.parseInt(strSongEnded);
		seekBar.setMax(seekMax);
		seekBar.setProgress(seekProgress);
		if (songEnded == 1) {
			btnPlayStop.setBackgroundResource(R.drawable.play);
		}
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		btnPlayStop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttonPlayStopOnClick();
			}

		});

		seekBar.setOnSeekBarChangeListener(this);
	}

	public void buttonPlayStopOnClick() {
		if (!isMusicPlaying) {
			btnPlayStop.setBackgroundResource(R.drawable.pause);
			playAudio();
			isMusicPlaying = true;
		} else if (isMusicPlaying) {
			btnPlayStop.setBackgroundResource(R.drawable.play);
			stopPlayingService();
		}
	}

	private void stopPlayingService() {
		// TODO Auto-generated method stub
		if (mBroadcastIsRegistered) {
			try {
				unregisterReceiver(broadCastReceiver);
				mBroadcastIsRegistered = false;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		try {
			stopService(serviceIntent);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		isMusicPlaying = false;
	}

	private void playAudio() {
		// TODO Auto-generated method stub
		checkConnectivity();
		if (isOnline) {
			stopPlayingService();
			serviceIntent.putExtra("audioLink", path);
			try {
				startService(serviceIntent);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			registerReceiver(broadCastReceiver, new IntentFilter(
					MyPlayService.BROADCAST_ACTION));
			mBroadcastIsRegistered = true;
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("No Network Connection");
			alertDialog.setMessage("Plesae connect to a network and try again");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			alertDialog.setIcon(R.drawable.about);
			btnPlayStop.setBackgroundResource(R.drawable.play);
			alertDialog.show();

		}
	}

	private void checkConnectivity() {
		ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting()
				|| cn.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.isConnectedOrConnecting()) {
			isOnline = true;
		} else
			isOnline = false;
	}

	private void showPD(Intent bufferIntent) {
		String bufferValue = bufferIntent.getStringExtra("Buffering");
		int bufferIntValue = Integer.parseInt(bufferValue);
		// when buffering value is 1 show buffering
		// progress dialog
		// when value is 0 dismiss dialogue
		switch (bufferIntValue) {
		case 0:
			if (pdBuff != null) {
				pdBuff.dismiss();
			}
			break;

		case 1:
			BufferDialogue();
			break;
		}

	}

	// progress dialogue
	private void BufferDialogue() {
		// TODO Auto-generated method stub
		pdBuff = ProgressDialog.show(Play.this, "Buffering...",
				"Loading the song...", true);
		pdBuff.setCancelable(true);
		//pdBuff.setCanceledOnTouchOutside(true);

	}

	// set up broadcast receiver
	private BroadcastReceiver broadcastBufferReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			showPD(intent);
		}
	};

	@Override
	protected void onPause() {
		if (mBufferingBroadcastIsRegisgtered) {
			unregisterReceiver(broadcastBufferReceiver);
			mBufferingBroadcastIsRegisgtered = false;
		}
		if (mBroadcastIsRegistered) {
			try {
				unregisterReceiver(broadCastReceiver);
				mBroadcastIsRegistered = false;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		super.onPause();
	}

	@Override
	protected void onResume() {

		if (!mBufferingBroadcastIsRegisgtered) {
			registerReceiver(broadcastBufferReceiver, new IntentFilter(
					MyPlayService.BROADCAST_BUFFER));
			mBufferingBroadcastIsRegisgtered = true;
		}
		if (!mBroadcastIsRegistered) {
			registerReceiver(broadCastReceiver, new IntentFilter(
					MyPlayService.BROADCAST_ACTION));
			mBroadcastIsRegistered = true;
		}
		super.onResume();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (fromUser) {
			int seekPos = seekBar.getProgress();
			intent.putExtra("seekpos", seekPos);
			sendBroadcast(intent);
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

}
