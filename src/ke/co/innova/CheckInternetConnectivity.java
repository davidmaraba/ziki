package ke.co.innova;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckInternetConnectivity {

	public static boolean isOnline;
	public ConnectivityManager cn;
	Context context;
	public CheckInternetConnectivity(Context context)
	{
		this.context=context;
	}
	public void checkConnectivity() {
		cn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting()
				|| cn.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.isConnectedOrConnecting()) {
			isOnline = true;
		} else
			isOnline = false;
	}
}
