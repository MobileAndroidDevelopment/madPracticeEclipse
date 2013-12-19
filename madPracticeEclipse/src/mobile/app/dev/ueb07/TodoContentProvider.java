package mobile.app.dev.ueb07;

import mobile.app.dev.ueb06.orm.TodoDBHelper;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

public class TodoContentProvider extends ContentProvider {

	private static final int PRIORITY_ID = 111;
	private static final int PRIORITIES = 110;
	private static final int TODO_ID = 101;
	private static final int TODOS = 100;
	public static final String SCHEME = "content://";
	public static final String AUTHORITY = "de.htwds.mada.todo";

	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		URI_MATCHER.addURI(AUTHORITY, Todos.PATH, TODOS);
		URI_MATCHER.addURI(AUTHORITY, Todos.PATH + "/#", TODO_ID);

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
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
				getAllTodos(projection, selection, selectionArgs, sortOrder);
				break;
			}
			case TODO_ID: {
				break;
			}
			case PRIORITIES: {
				break;
			}
			case PRIORITY_ID: {
				break;
			}
			default: {
				return null;
			}
		}

		return null;
	}

	private Cursor getAllTodos(String[] projection, String selection, String[] selectionArguments, String sortOrder) {
		TodoDBHelper todoDBHelper = null;
		try {
			todoDBHelper = new TodoDBHelper();
			SQLiteDatabase database = todoDBHelper.getReadableDatabase();
			return database.query("", projection, selection, selectionArguments, null, null, sortOrder);
		} catch (Exception e) {
			return null;
		} finally {
			todoDBHelper.close();
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final class Todos implements BaseColumns {
		public static final String PATH = "todo";
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + PATH);
	}

	public static final class Priorities implements BaseColumns {
		public static final String PATH = "priority";
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + PATH);
	}

}
