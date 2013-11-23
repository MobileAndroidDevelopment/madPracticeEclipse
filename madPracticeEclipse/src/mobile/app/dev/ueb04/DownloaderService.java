package mobile.app.dev.ueb04;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class DownloaderService extends IntentService {

	public static final String URL_KEY = "URL";
	private static final String TITLE = "File-Download";
	private int downloadPercentage = 0;
	private final IBinder downloaderBinder = new DownloaderBinder();
	
	private int errorCase=0;
	public static final int OK = 0;
	public static final int HTTP_NOT_OK = 1;
	public static final int SAVING_NOT_POSSIBLE=2;
	public static final int UNKNOWN_ERROR = 3;

	public DownloaderService() {
		super(TITLE);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		errorCase=OK;
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
				throw new HttpException("HTTP nicht ok! !=200");
			}

			// this will be useful to display download percentage
			// might be -1: server did not report the length
			int fileLength = connection.getContentLength();
			// TODO: fileLength -1 abfangen

			// download the file
			input = connection.getInputStream();
			// TODO: Dateiname raussuchen
			
			output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/file_name.extension");
			String fileName = downloadUrl.substring(downloadUrl.lastIndexOf('/')+1);
			Log.d("DOWNLOAD", "Schreibe Datei nach: "+Environment.getExternalStorageDirectory().getPath() +fileName);

			byte data[] = new byte[4096];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				if (fileLength > 0) { // only if total length is known
					downloadPercentage = (int) (total * 100 / fileLength);
				}
				output.write(data, 0, count);
			}
			output.flush();
			errorCase = OK;
			Log.d("DOWNLOAD", "Prozent: "+downloadPercentage);
		} catch(HttpException e){
			Log.d("DOWNLOAD", "Problem bei der HTTP Verbindung");
			errorCase = HTTP_NOT_OK;
		} catch(IOException e) {
			Log.d("DOWNLOAD", "Schreiben der Datei nicht moeglich");
			errorCase = SAVING_NOT_POSSIBLE;
		}catch (Exception e) {
			Log.e("DOWNLOAD", "Download leider nicht erfolgreich!" +e.getMessage());
			errorCase = UNKNOWN_ERROR;
		} finally {
			try {
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			} catch (IOException ignored) {
			}

			Log.d("DOWNLOAD", "Download wurde abgeschlossen");
			if (connection != null)
				connection.disconnect();
		}
	}

	public int getPercentage() {
		return downloadPercentage;
	}
	
	public int getErrorCase(){
		return errorCase;
	}
	
	public boolean isOk(){
		return errorCase==OK;
	}

	public class DownloaderBinder extends Binder {
		public DownloaderService getService() {
			return DownloaderService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return downloaderBinder;
	}
}
