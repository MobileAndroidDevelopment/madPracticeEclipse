package mobile.app.dev.ueb05;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;


public class Alarm extends BroadcastReceiver {
	
	public static final String BC_ACTION = "MAD_BROADCAST";
	
	@SuppressWarnings("deprecation")
	@SuppressLint("Wakelock")
	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		//PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");		
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK 
												| PowerManager.ACQUIRE_CAUSES_WAKEUP 
												| PowerManager.SCREEN_BRIGHT_WAKE_LOCK
												| PowerManager.ON_AFTER_RELEASE , "");		
		wl.acquire();
		Log.d("alarm ausgelöst", "test");

		Intent snoozeIntent = new Intent(context, SnoozeActivity.class);
		snoozeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);		
		context.startActivity(snoozeIntent);

		wl.release();
	}
	
	
}
