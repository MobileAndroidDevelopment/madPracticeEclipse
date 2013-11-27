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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Download-Activity zum Herunterladen einer Datei über eine URL.
 */
public class DownloadActivity extends Activity {

	/** der eigentliche Service zum Downloaden */
	private DownloaderService downloaderService;
	/** Service gebunden oder nicht */
	private boolean serviceBound;
	/** Intent für den Downloader-Service */
	private Intent downloaderServiceIntent;
	
	private ProgressBar progressBar;
	private Button downloadButton;

	/** Verbindung zum DownloaderService */
	private ServiceConnection serviceConnection = new DownloaderServiceConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		progressBar = (ProgressBar) findViewById(R.id.progressBarDownload);
		downloadButton = (Button) findViewById(R.id.buttonDownload);
		downloaderServiceIntent = new Intent(this, DownloaderService.class);
		bindService(downloaderServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	/**
	 * Dient dem Starten des Downloads. Hier wird zunaechst ueberprüft, ob der Link auch nicht leer ist, dann wird einfach eine Meldung ausgegeben.
	 * <p> Zum Starten des Downloads wird der DownloaderService gebunden und gestartet. Gab es beim Binden Probleme, so wird direkt eine Meldung ausgegeben,
	 * ansonsten wird eine Meldung ausgegeben, dass der Download nun gestartet wird.
	 * @param view
	 */
	public void startDownload(View view) {
		String url = ((EditText) findViewById(R.id.editTextDownloadLink)).getText().toString();
		if (url == null || "".equals(url)) {
			Toast.makeText(this, R.string.supplyUrl, Toast.LENGTH_SHORT).show();
		} else {
			Log.d("DOWNLOAD", "Starte Download von " + url);
			downloaderServiceIntent.putExtra(DownloaderService.URL_KEY, url);
			startService(downloaderServiceIntent);
			Toast.makeText(this, R.string.DOWNLOAD_STARTED, Toast.LENGTH_LONG).show();
			downloadButton.setEnabled(false);
			automaticRefresh();
		}
	}

	private void setDownloadButtonEnabled(final boolean enabled) {
		runOnUiThread(new Runnable() {
			public void run() {
				downloadButton.setEnabled(enabled);
			}
		});
	}

	/**
	 * Frägt den Prozentwert des Downloads ab und setzt entsprechend die Progressbar. Ist der Download fertig,
	 * wird der Service unbound und gestoppt, der Downloadbutton wieder aktiv und die Progressbar auf 0 gesetzt.
	 * Ist er noch nicht fertig und die Dateigröße nicht bekannt, so wird eine Meldung ausgegeben.
	 */
	public void refresh() {
		if (serviceBound) {
			if (downloaderService.isOk()) {
				int percentage = downloaderService.getPercentage();
				progressBar.setProgress(percentage);
				if (downloaderService.hasFinished()) {
					refreshFinished();
					stopService(downloaderServiceIntent);
				} else if (downloaderService.fileSizeUnknown()) {
					Toast.makeText(this, R.string.FILE_SIZE_UNKNOWN, Toast.LENGTH_SHORT).show();
				}
			} else {
				showErrorText();
			}
		}
	}

	/**
	 * Refreshed die Oberfläche nachdem der Download beendet wurde. Dies muss über runOnUiThread geregelt werden,
	 * da die Funktion nicht vom MainThread aufgerufen wird, Oberflächenänderungen jedoch nur vom MainThread durchgeführt
	 * werden dürfen.
	 */
	private void refreshFinished() {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(DownloadActivity.this, R.string.DOWNLOAD_FINISHED, Toast.LENGTH_LONG).show();
				downloadButton.setEnabled(true);
				progressBar.setProgress(0);
			}
		});
	}

	private void showErrorText() {
		String errorText = "";
		switch (downloaderService.getErrorCase()) {
			case DownloaderService.HTTP_NOT_OK: {
				errorText = getString(R.string.HTTP_PROBLEM);
				break;
			}
			case DownloaderService.SAVING_NOT_POSSIBLE: {
				errorText = getString(R.string.READ_WRITE_ERROR);
				break;
			}
			case DownloaderService.UNKNOWN_ERROR: {
				errorText = getString(R.string.UNKNOWN_ERROR);
				break;
			}
		}
		Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		Log.d("RESUME", "Wieder da! ");
		if (downloaderServiceIntent != null) {
			bindService(downloaderServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
			isRefreshing = false;
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		Log.d("DESTROY", "Connection wird zerstört");
		if (serviceBound) {
			unbindService(serviceConnection);
			serviceBound = false;
		}
		super.onDestroy();
	}

	/**
	 * Callback für das Binden des Downloader-Services.<p>
	 * Ist der Service verbunden, so wird der automatische Refresh für die Oberfläche gestartet.
	 */
	private class DownloaderServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			DownloaderBinder downloaderBinder = (DownloaderBinder) service;
			downloaderService = downloaderBinder.getService();
			serviceBound = true;
			automaticRefresh();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("SERVICE-DISCONNECTED", "Service Bound = false");
			serviceBound = false;
		}
	}

	private boolean isRefreshing = false;

	/**
	 * Automatisches Aktualisieren der Oberfläche während des Downloads. Dies muss als eigener Thread laufen, damit der
	 * MainThread nicht blockiert wird. Der Refresh-Vorgang kann zur Laufzeit immer nur genau einmal durchlaufen werden.
	 */
	private void automaticRefresh() {
		Log.d("AUTOREFRESH", "IsRe " + isRefreshing + ", downloader: " + (downloaderService != null) + ", hasFinsihed=" + downloaderService.hasFinished());
		if (!isRefreshing && downloaderService != null) {
			isRefreshing = true;
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(200);
						while (!downloaderService.hasFinished()) {
							Thread.sleep(10);
							setDownloadButtonEnabled(false);
							refresh();
						}
					} catch (InterruptedException e1) {}
					isRefreshing = false;
				}
			}).start();
		}
	}
}
