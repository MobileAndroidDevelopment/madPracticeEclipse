package mobile.app.dev.ueb04;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class DownloaderService extends Service {

	private final IBinder binder = new LocalBinder();
	
	public class LocalBinder extends Binder{
		
		public DownloaderService getService(){
			return DownloaderService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	// TODO: Download starten etc
}
