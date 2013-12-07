package mobile.app.dev.ueb06.orm;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

public class PriorityDBHelper extends AbstractDBHelper {

	public List<Priority> getAllPriorites(Context context) throws SQLException {
		Log.i(Priority.class.getName(), "Show list again");
		RuntimeExceptionDao<Priority, Integer> dao = getHelper(context).getPriorityDao();
		QueryBuilder<Priority, Integer> builder = dao.queryBuilder();
		List<Priority> list = dao.query(builder.prepare());
		Log.d("PriorityDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	public void createOrUpdate(Context context, Priority priority) {
		RuntimeExceptionDao<Priority, Integer> dao = getHelper(context).getPriorityDao();
		dao.createOrUpdate(priority);
	}

	public void delete(Context context, Priority priority) {
		RuntimeExceptionDao<Priority, Integer> dao = getHelper(context).getPriorityDao();
		dao.delete(priority);
	}
}
