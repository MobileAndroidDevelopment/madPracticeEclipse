package mobile.app.dev.moneysac.Model;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class SacEntryDBHelper extends AbstractDBHelper<SacEntry> {

	public List<SacEntry> getAll(Context context) throws SQLException {
		Log.i(SacEntry.class.getName(), "Show list again");
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		QueryBuilder<SacEntry, Integer> builder = dao.queryBuilder();
		List<SacEntry> list = dao.query(builder.prepare());
		Log.d("CategoryDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	public void createOrUpdate(Context context, SacEntry category) throws SQLException {
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		dao.createOrUpdate(category);
	}

	public void delete(Context context, SacEntry category) throws SQLException {
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		dao.delete(category);
	}

	public List<SacEntry> where(Context context, String column, Object value) throws SQLException {
		Dao<SacEntry, Integer> dao = getHelper(context).getSacEntryDao();
		List<SacEntry> categories = dao.queryBuilder().where().eq(column, value).query();
		return categories;
	}
}
