package mobile.app.dev.ueb06;

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

	@Override
	public String toString() {
		return "Todo [id=" + id + ", date=" + date + ", title=" + title + ", description=" + description + "]";
	}

}
