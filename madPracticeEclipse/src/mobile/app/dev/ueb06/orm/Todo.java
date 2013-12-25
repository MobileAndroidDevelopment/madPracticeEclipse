package mobile.app.dev.ueb06.orm;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Todo implements Serializable {

	@DatabaseField(generatedId = true)
	private int _id;
	@DatabaseField
	private long datetime;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Category category;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Priority priority;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return title;
	}

}
