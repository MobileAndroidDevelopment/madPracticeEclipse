package mobile.app.dev.ueb05;

import mobile.app.dev.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class SnoozeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snooze);
		//		MediaPlayer player = MediaPlayer.create(context, R.raw.);
		//		player.setLooping(true);
		//		player.start();
		//		SystemClock.sleep(20000);
		//		player.stop();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.snooze, menu);
		return true;
	}

}
