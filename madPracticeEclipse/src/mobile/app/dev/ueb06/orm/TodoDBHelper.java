package mobile.app.dev.ueb06.orm;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class TodoDBHelper extends AbstractDBHelper<Todo> {

	public List<Todo> getAll(Context context) throws SQLException {
		Log.d(Todo.class.getName(), "Todos laden");
		Dao<Todo, Integer> dao = getHelper(context).getTodoDao();
		QueryBuilder<Todo, Integer> builder = dao.queryBuilder();
		List<Todo> list = dao.query(builder.prepare());
		Log.d("TodoDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	public void createOrUpdate(Context context, Todo todo) throws SQLException {
		Dao<Todo, Integer> dao = getHelper(context).getTodoDao();
		dao.createOrUpdate(todo);
	}

	public void delete(Context context, Todo todo) throws SQLException {
		Dao<Todo, Integer> dao = getHelper(context).getTodoDao();
		dao.delete(todo);
	}
	

	public List<Todo> where(Context context, String column, Object value) throws SQLException{
		Dao<Todo, Integer> dao = getHelper(context).getTodoDao();
		List<Todo> categories =dao.queryBuilder().where().eq(column, value).query();
		return categories;
	}
}
