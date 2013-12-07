package mobile.app.dev.ueb06.orm;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

public class CategoryDBHelper extends AbstractDBHelper {
	
	public List<Category> getAllCategories(Context context) throws SQLException {
		Log.i(Category.class.getName(), "Show list again");
		RuntimeExceptionDao<Category, Integer> dao = getHelper(context).getCategoryDao();
		QueryBuilder<Category, Integer> builder = dao.queryBuilder();
		List<Category> list = dao.query(builder.prepare());
		Log.d("CategoryDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}
}
