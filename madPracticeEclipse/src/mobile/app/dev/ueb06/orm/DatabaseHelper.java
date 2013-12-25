package mobile.app.dev.ueb06.orm;

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

	public static final String TODO_TABLE = "todo";
	public static final String PRIORITY_TABLE = "priority";
	
	private static final String HAUSHALT = "Haushalt";
	private static final String HIGH = "hoch";
	private static final String MEDIUM = "mittel";
	private static final String LOW = "niedrig";
	private static final String UNI = "Uni";
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "todo.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 6;

	// the DAO object we use to access the Todo table
	private Dao<Todo, Integer> todoDao = null;
	private Dao<Priority, Integer> priorityDao = null;
	private Dao<Category, Integer> categoryDao = null;

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
			TableUtils.createTable(connectionSource, Todo.class);
			TableUtils.createTable(connectionSource, Priority.class);
			TableUtils.createTable(connectionSource, Category.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

		createPriorites();
		createCategories();
	}

	private void createCategories() {
		try {
			Dao<Category, Integer> dao = getCategoryDao();
			Category uniCategory = new Category();
			uniCategory.setName(UNI);
			dao.create(uniCategory);

			Category homeCategory = new Category();
			homeCategory.setName(HAUSHALT);
			dao.create(homeCategory);
		} catch (SQLException e) {
			Log.e("CATEGORY_CREATE", e.getMessage(), e);
		}
	}

	private void createPriorites() {
		try {
		Dao<Priority, Integer> dao = getPriorityDao();
		Priority lowPrio = new Priority(LOW);
		dao.create(lowPrio);

		Priority mediumPrio = new Priority(MEDIUM);
		dao.create(mediumPrio);

		Priority highPrio = new Priority(HIGH);
		dao.create(highPrio);
		} catch (SQLException e) {
			Log.e("PRIORITY_CREATE", e.getMessage(), e);
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
			TableUtils.dropTable(connectionSource, Todo.class, true);
			TableUtils.dropTable(connectionSource, Priority.class, true);
			TableUtils.dropTable(connectionSource, Category.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Todo class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 * @throws SQLException 
	 */
	public Dao<Todo, Integer> getTodoDao() throws SQLException {
		if (todoDao == null) {
			todoDao = getDao(Todo.class);
		}
		return todoDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our Todo class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 * @throws SQLException 
	 */
	public Dao<Priority, Integer> getPriorityDao() throws SQLException {
		if (priorityDao == null) {
			priorityDao = getDao(Priority.class);
		}
		return priorityDao;
	}

	public Dao<Category, Integer> getCategoryDao() throws SQLException {
		if (categoryDao == null) {
			categoryDao = getDao(Category.class);
		}
		return categoryDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		todoDao = null;
		priorityDao = null;
	}

}