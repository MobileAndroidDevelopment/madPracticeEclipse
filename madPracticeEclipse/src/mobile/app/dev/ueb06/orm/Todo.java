package mobile.app.dev.ueb06.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Todo {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private long date;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
	@DatabaseField(canBeNull = false, foreign=true, foreignAutoRefresh = true)
	private Category category;
	@DatabaseField(canBeNull = false, foreign=true, foreignAutoRefresh = true)
	private Priority priority;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
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
		return "Todo [id=" + id + ", date=" + date + ", title=" + title + ", description=" + description + "]";
	}

}
