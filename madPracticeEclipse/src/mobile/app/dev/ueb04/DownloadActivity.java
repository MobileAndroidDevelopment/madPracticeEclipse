package mobile.app.dev.ueb04;

import mobile.app.dev.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DownloadActivity extends Activity {

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
		Intent intent = new Intent(this, DownloaderService.class);
		intent.putExtra(DownloaderService.URL_KEY, url);
		startService(intent);
	}

}
