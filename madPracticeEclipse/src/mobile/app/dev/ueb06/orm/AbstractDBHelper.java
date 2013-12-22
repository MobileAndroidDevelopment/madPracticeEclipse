package mobile.app.dev.ueb06.orm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Hilft bei Standardfunktionen, um in den Activities nicht immer das volle Prozedere mit dem DatabaseHelper durchzufuehren
 */
public abstract class AbstractDBHelper<T extends Serializable> {

	private DatabaseHelper databaseHelper = null;

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
	
	public SQLiteDatabase getReadableDatabase(){
		return databaseHelper.getReadableDatabase();
	}
	
	public SQLiteDatabase getWritableDatabase(){
		return databaseHelper.getWritableDatabase();
	}

	public abstract List<T> getAll(Context context) throws SQLException;

	public abstract void createOrUpdate(Context context, T t) throws SQLException;

	public abstract void delete(Context context, T t) throws SQLException;
}
