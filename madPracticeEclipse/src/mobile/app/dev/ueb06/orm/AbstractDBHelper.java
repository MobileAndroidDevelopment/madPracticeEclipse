package mobile.app.dev.ueb06.orm;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Hilft bei Standardfunktionen, um in den Activities nicht immer das volle Prozedere mit dem DatabaseHelper durchzufuehren
 */
public abstract class AbstractDBHelper {

	protected DatabaseHelper databaseHelper = null;

	public DatabaseHelper getHelper(Context context) {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	public void close() {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}
}
