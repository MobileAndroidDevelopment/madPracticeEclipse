package mobile.app.dev.ueb05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {
	
	public static final String BC_ACTION = "MAD_BROADCAST";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
		wl.acquire();
		Log.d("alarm ausgel�st", "test");

		Intent snoozeIntent = new Intent(context, SnoozeActivity.class);
		snoozeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(snoozeIntent);

//		Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

		wl.release();
	}
}
