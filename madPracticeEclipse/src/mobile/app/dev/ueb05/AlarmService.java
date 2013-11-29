package mobile.app.dev.ueb05;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service{
    Alarm alarm = new Alarm();
    public void onCreate()
    {
        super.onCreate();       
    }

    public void onStart(Context context, long timeInMillis)
    {
        alarm.setAlarm(context, timeInMillis);
    }

	@Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }
}
