package mobile.app.dev.ueb04;

import mobile.app.dev.R;
import mobile.app.dev.ueb04.DownloaderService.DownloaderBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * TODO: Refresh-Button
 * TODO: Settings für Standard-Ordner
 * TODO: Zugriff auf Service nachdem gestartet wurde
 * TODO: Service binden
 * @author Benne
 */
public class DownloadActivity extends Activity {

	private DownloaderService downloaderService;
	private boolean serviceBound;
	private Intent downloaderServiceIntent;

	/** Verbindung zum DownloaderService */
	private ServiceConnection connection = new DownloaderServiceConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.download, menu);
		return true;
	}

	public void startDownload(View view) {
		String url = ((EditText) findViewById(R.id.editTextDownloadLink)).getText().toString();
		downloaderServiceIntent = new Intent(this, DownloaderService.class);
		downloaderServiceIntent.putExtra(DownloaderService.URL_KEY, url);
		startService(downloaderServiceIntent);

		bindService(downloaderServiceIntent, connection, Context.BIND_AUTO_CREATE);
	}
	
	public void refresh(){
		if(serviceBound){
			int percentage = downloaderService.getPercentage();
			// TODO: progressbar updaten
		}
	}

	@Override
	protected void onResume() {
		bindService(downloaderServiceIntent, connection, Context.BIND_AUTO_CREATE);
		refresh();
	}

	private class DownloaderServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			DownloaderBinder downloaderBinder = (DownloaderBinder) service;
			downloaderService = downloaderBinder.getService();
			serviceBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serviceBound = false;
		}
	}
}
