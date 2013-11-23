package mobile.app.dev.ueb04;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class DownloaderService extends IntentService {

	public static final String URL_KEY = "URL";
	private static final String TITLE = "File-Download";

	public DownloaderService() {
		super(TITLE);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String downloadUrl = intent.getStringExtra(URL_KEY);
		InputStream input = null;
		OutputStream output = null;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(downloadUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();

			// expect HTTP200
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				Log.e("DOWNLOAD", "keine Connection!");
				// TODO: Fehlerhandling
			}

			// this will be useful to display download percentage
			// might be -1: server did not report the length
			int fileLength = connection.getContentLength();

			// download the file
			input = connection.getInputStream();
			output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/file_name.extension");

			byte data[] = new byte[4096];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				if (fileLength > 0) { // only if total length is known
				//                        onProgressUpdate((int) (total * 100 / fileLength));
					;
					// TODO: Download-Status publishen
				}
				output.write(data, 0, count);
			}
		} catch (Exception e) {
			Log.e("DOWNLOAD", e.getMessage());
		} finally {
			try {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			} catch (IOException ignored) {
			}

			if (connection != null)
				connection.disconnect();
		}
	}
}
