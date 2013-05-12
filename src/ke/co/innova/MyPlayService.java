package ke.co.innova;

import java.security.PublicKey;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.SeekBar;
import android.widget.Toast;

public class MyPlayService extends Service implements OnPreparedListener,
		OnCompletionListener, OnErrorListener, OnSeekCompleteListener,
		OnBufferingUpdateListener, OnInfoListener {
	private MediaPlayer mediaPlayer = new MediaPlayer();
	String setAudioLink;

	private static final int NOTIFICATION_ID = 1;

	private boolean isPausedInCall = false;
	private PhoneStateListener phoneStateListener;
	private TelephonyManager telephneManager;

	public static final String BROADCAST_BUFFER = "com.finalplayer.broadcastbuffer";
	Intent bufferIntent;

	Intent seekIntent;
	String sentSeekPos;

	int mediaPosition;

	int intSeekPos;
	int mediaMax;
	private final Handler handler = new Handler();
	private static int songEnded;
	public static final String BROADCAST_ACTION = "com.finalplayer.seekprogress";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		bufferIntent = new Intent(BROADCAST_BUFFER);

		seekIntent = new Intent(BROADCAST_ACTION);

		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnBufferingUpdateListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);
		mediaPlayer.setOnInfoListener(this);
		mediaPlayer.reset();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// receiver for seeker change
		registerReceiver(broadcastReceiver, new IntentFilter(
				Play.BROADCAST_SEEKBAR));

		// TODO Auto-generated method stub
		telephneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		phoneStateListener = new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				switch (state) {
				case TelephonyManager.CALL_STATE_OFFHOOK:
				case TelephonyManager.CALL_STATE_RINGING:
					if (mediaPlayer != null) {
						pauseMedia();
						isPausedInCall = true;

					}

					break;
				case TelephonyManager.CALL_STATE_IDLE:
					if (mediaPlayer != null) {
						if (isPausedInCall) {
							isPausedInCall = false;
							playMedia();
						}
					}
					break;

				}
			}
		};

		telephneManager.listen(phoneStateListener,
				PhoneStateListener.LISTEN_CALL_STATE);

		initNofification();

		setAudioLink = intent.getExtras().getString("audioLink");
		mediaPlayer.reset();
		if (!mediaPlayer.isPlaying()) {
			try {
				mediaPlayer.setDataSource(
						setAudioLink);

				// notify that it is loading
				sendBufferingBroadcast();

				mediaPlayer.prepareAsync();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}

		setUpHandler();

		return START_STICKY;
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			updateSeekPos(intent);
		}
	};

	protected void updateSeekPos(Intent intent) {
		// TODO Auto-generated method stub
		int seekPos = intent.getIntExtra("seekpos", 0);
		if (mediaPlayer.isPlaying()) {
			handler.removeCallbacks(sendUpdatesToUI);
			mediaPlayer.seekTo(seekPos);
			setUpHandler();
		}
	}

	private void setUpHandler() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(sendUpdatesToUI);
		handler.postDelayed(sendUpdatesToUI, 1000);

	}

	private Runnable sendUpdatesToUI = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			LogMediaPosition();
			handler.postDelayed(this, 1000);
		}
	};

	private void sendBufferingBroadcast() {
		// TODO Auto-generated method stub
		bufferIntent.putExtra("Buffering", "1");
		sendBroadcast(bufferIntent);

	}

	protected void LogMediaPosition() {
		// TODO Auto-generated method stub
		if (mediaPlayer.isPlaying()) {
			mediaPosition = mediaPlayer.getCurrentPosition();
			mediaMax = mediaPlayer.getDuration();
			seekIntent.putExtra("counter", String.valueOf(mediaPosition));
			seekIntent.putExtra("mediamax", String.valueOf(mediaMax));
			seekIntent.putExtra("song_ended", String.valueOf(songEnded));
			sendBroadcast(seekIntent);
		}
	}

	private void sendBufferingCompleteBroadcast() {
		bufferIntent.putExtra("Buffering", "0");
		sendBroadcast(bufferIntent);
	}

	protected void pauseMedia() {
		// TODO Auto-generated method stub
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	private void initNofification() {
		// TODO Auto-generated method stub
		String note = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManger = (NotificationManager) getSystemService(note);
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = "Music playing";
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		Context context = getApplicationContext();
		CharSequence contentTitle = "Ziki Music Playing";
		CharSequence contentText = "Listen and Enjoy awesome Ziki Music";
		Intent notificationIntent = new Intent(this, Play.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		mNotificationManger.notify(NOTIFICATION_ID, notification);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			mediaPlayer.release();
		}
		if (phoneStateListener != null) {
			telephneManager.listen(phoneStateListener,
					PhoneStateListener.LISTEN_NONE);
		}
		cancelNotification();

		unregisterReceiver(broadcastReceiver);
		// Unregister handler receiver
		// unregisterReceiver(headsetReceiver);

		handler.removeCallbacks(sendUpdatesToUI);

		// stop the need to tell activity to display "play" button
		// resetButtonPlayStopBroadcast();
	}

	private void cancelNotification() {
		// TODO Auto-generated method stub
		String as = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(as);
		mNotificationManager.cancel(NOTIFICATION_ID);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			Toast.makeText(this,
					"MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK" + extra,
					Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Toast.makeText(this, "MEDIA_ERROR_SERVER_DIED" + extra,
					Toast.LENGTH_SHORT).show();

			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Toast.makeText(this, "MEDIA_ERROR_UNKNOWN" + extra,
					Toast.LENGTH_SHORT).show();
			break;

		}
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		stopMedia();
		stopSelf();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		sendBufferingCompleteBroadcast();
		playMedia();
	}

	public void playMedia() {
		// TODO Auto-generated method stub
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	public void stopMedia() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
		if (!mediaPlayer.isPlaying()) {
			playMedia();
		}
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

}
