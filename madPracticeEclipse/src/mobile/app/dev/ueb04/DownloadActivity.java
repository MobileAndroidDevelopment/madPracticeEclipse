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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * TODO: Settings für Standard-Ordner
 * TODO: Zugriff auf Service nachdem gestartet wurde
 */
public class DownloadActivity extends Activity {

	private DownloaderService downloaderService;
	private boolean serviceBound;
	private Intent downloaderServiceIntent;
	private ProgressBar progressBar;

	/** Verbindung zum DownloaderService */
	private ServiceConnection serviceConnection = new DownloaderServiceConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		progressBar = (ProgressBar) findViewById(R.id.progressBarDownload);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.download, menu);
		return true;
	}

	public void startDownload(View view) {
		String url = ((EditText) findViewById(R.id.editTextDownloadLink)).getText().toString();
		if (url == null || "".equals(url)) {
			Toast.makeText(this, R.string.supplyUrl, Toast.LENGTH_SHORT).show();
		} else {
			Log.d("DOWNLOAD", "Starte Download von " + url);
			downloaderServiceIntent = new Intent(this, DownloaderService.class);
			downloaderServiceIntent.putExtra(DownloaderService.URL_KEY, url);
			startService(downloaderServiceIntent);
			bindService(downloaderServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		}
	}

	public void refresh() {
		if (serviceBound) {
			int percentage = downloaderService.getPercentage();
			progressBar.setProgress(percentage);
			Log.d("REFRESH", "Prozent: " + percentage);
			// TODO: progressbar updaten
			if (percentage >= 100) {
				Toast.makeText(this, R.string.DOWNLOAD_FINISHED, Toast.LENGTH_LONG).show();
				unbindService(serviceConnection);
				stopService(downloaderServiceIntent);
			}
		} else {
			// nix eigentlich
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.downloader_refresh:
				refresh();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		Log.d("RESUME", "Wieder da! ");
		if (downloaderServiceIntent != null) {
			bindService(downloaderServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
			refresh();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		Log.d("DESTROY", "Connection wird zerstört");
		unbindService(serviceConnection);
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
