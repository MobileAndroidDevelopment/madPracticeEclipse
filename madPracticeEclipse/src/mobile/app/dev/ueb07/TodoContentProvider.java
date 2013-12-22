package mobile.app.dev.ueb07;

import mobile.app.dev.ueb06.orm.AbstractDBHelper;
import mobile.app.dev.ueb06.orm.DatabaseHelper;
import mobile.app.dev.ueb06.orm.PriorityDBHelper;
import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class TodoContentProvider extends ContentProvider {

	private static final int PRIORITY_ID = 111;
	private static final int PRIORITIES = 110;
	private static final int TODO_ID = 101;
	private static final int TODOS = 100;
	public static final String SCHEME = "content://";
	/** Paketstruktur scheint Pflicht für die Abgabe zu sein, muss aber nur nach aussen hin so aussehen */
	public static final String AUTHORITY = "de.htwds.mada.todo";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		// Todo-URIs
		URI_MATCHER.addURI(AUTHORITY, Todos.PATH, TODOS);
		URI_MATCHER.addURI(AUTHORITY, Todos.PATH + "/#", TODO_ID);

		// Priority-URIs
		URI_MATCHER.addURI(AUTHORITY, Todos.PATH, PRIORITIES);
		URI_MATCHER.addURI(AUTHORITY, Todos.PATH + "/#", PRIORITY_ID);
	}

	public static final String TODO_URI = "content://de.htwds.mada.todo/todos";
	public static final String PRIORITY_URI = "content://de.htwds.mada.todo/priorities";

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		int uriCode = URI_MATCHER.match(uri);
		switch (uriCode) {
			case TODOS:
				return Todos.CONTENT_TYPE;
			case TODO_ID:
				return Todos.CONTENT_TYPE_ITEM;
			case PRIORITIES:
				return Priorities.CONTENT_TYPE;
			case PRIORITY_ID:
				return Priorities.CONTENT_TYPE_ITEM;
			default:
				return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriCode = URI_MATCHER.match(uri);
		switch (uriCode) {
			case TODOS:
				long newTodoId = insertData(values, new TodoDBHelper(), DatabaseHelper.TODO_TABLE);
				// URI für neues Todo erstellen und zurückgeben 
				return ContentUris.appendId(uri.buildUpon(), newTodoId).build();
			case PRIORITIES:
				long newPriorityId = insertData(values, new PriorityDBHelper(), DatabaseHelper.PRIORITY_TABLE);
				// URI für neues Todo erstellen und zurückgeben 
				return ContentUris.appendId(uri.buildUpon(), newPriorityId).build();
			default:
				return null;
		}
	}

	/**
	 * Fügt einen Datensatz hinzu und gibt die eingefügte ID zurück.
	 * @param values
	 * @param dbHelper
	 * @return die eingefügte ID
	 */
	private long insertData(ContentValues values, AbstractDBHelper dbHelper, String table) {
		SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
		long id = sqlDB.insert(table, null, values);
		sqlDB.close();
		return id;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		int uriCode = URI_MATCHER.match(uri);
		switch (uriCode) {
			case TODOS: {
				SelectionArguments arguments = new SelectionArguments(DatabaseHelper.TODO_TABLE)
						.setProjection(projection).addSelection(selection).setSelectionArguments(selectionArgs).setSortOrder(sortOrder);
				return getAll(arguments, new TodoDBHelper());
			}
			case TODO_ID: {
				long todoId = ContentUris.parseId(uri);
				String idSelection = TodoDBHelper.COL_ID + "=" + todoId;
				SelectionArguments arguments = new SelectionArguments(DatabaseHelper.TODO_TABLE)
						.setProjection(projection)
						.addSelection(selection).addSelection(idSelection)
						.setSelectionArguments(selectionArgs)
						.setSortOrder(sortOrder);
				return getAll(arguments, new TodoDBHelper());
			}
			case PRIORITIES: {
				SelectionArguments arguments = new SelectionArguments(DatabaseHelper.PRIORITY_TABLE)
						.setProjection(projection).addSelection(selection).setSelectionArguments(selectionArgs).setSortOrder(sortOrder);
				return getAll(arguments, new PriorityDBHelper());
			}
			case PRIORITY_ID: {
				long priorityId = ContentUris.parseId(uri);
				String idSelection = PriorityDBHelper.COL_ID + "=" + priorityId;
				SelectionArguments arguments = new SelectionArguments(DatabaseHelper.PRIORITY_TABLE)
						.setProjection(projection)
						.addSelection(selection).addSelection(idSelection)
						.setSelectionArguments(selectionArgs)
						.setSortOrder(sortOrder);
				return getAll(arguments, new PriorityDBHelper());
			}
			default: {
				return null;
			}
		}
	}

	private Cursor getAll(SelectionArguments arguments, AbstractDBHelper dbHelper) {
		try {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			return database.query(arguments.getTableName(), arguments.getProjection(), arguments.getSelection(), arguments.getSelectionArguments(), null, null,
					arguments.getSortOrder());
		} catch (Exception e) {
			Log.e("CONTENT_PROVIDER", "error querying database", e);
			return null;
		} finally {
			dbHelper.close();
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final class Todos implements BaseColumns {
		public static final String PATH = "todo";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(TodoContentProvider.CONTENT_URI, PATH);
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.de.htwds.mada.todo";
		public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.de.htwds.mada.todo";
	}

	public static final class Priorities implements BaseColumns {
		public static final String PATH = "priority";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(TodoContentProvider.CONTENT_URI, PATH);
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.de.htwds.mada.priority";
		public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.de.htwds.mada.priority";
	}

}
