package mobile.app.dev.moneysac.Model;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "sacEntry.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the Todo table
	private Dao<Category, Integer> categoryDao = null;
	private Dao<SacEntryType, Integer> sacEntryTypeDao = null;
	private Dao<SacEntry, Integer> sacEntryDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, SacEntryType.class);
			TableUtils.createTable(connectionSource, Category.class);
			TableUtils.createTable(connectionSource, SacEntry.class);

			createEntryTypes();
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	private void createEntryTypes() {
		try {
			Dao<SacEntryType, Integer> dao = getSacEntryTypeDao();
			SacEntryType incomeEntryType = new SacEntryType();
			incomeEntryType.setName("Einnahme");
			dao.create(incomeEntryType);

			SacEntryType expenseEntryType = new SacEntryType();
			expenseEntryType.setName("Ausgabe");
			dao.create(expenseEntryType);
		} catch (SQLException e) {
			Log.e("ENTRY_TYPE_CREATE", e.getMessage(), e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, SacEntryType.class, true);
			TableUtils.dropTable(connectionSource, Category.class, true);
			TableUtils.dropTable(connectionSource, SacEntry.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public Dao<Category, Integer> getCategoryDao() throws SQLException {
		if (categoryDao == null) {
			categoryDao = getDao(Category.class);
		}
		return categoryDao;
	}

	public Dao<SacEntryType, Integer> getSacEntryTypeDao() throws SQLException {
		if (sacEntryTypeDao == null) {
			sacEntryTypeDao = getDao(SacEntryType.class);
		}
		return sacEntryTypeDao;
	}

	public Dao<SacEntry, Integer> getSacEntryDao() throws SQLException {
		if (sacEntryDao == null) {
			sacEntryDao = getDao(SacEntry.class);
		}
		return sacEntryDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		categoryDao = null;
		sacEntryDao = null;
		sacEntryTypeDao = null;
	}

}