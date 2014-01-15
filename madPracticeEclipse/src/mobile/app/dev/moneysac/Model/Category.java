package mobile.app.dev.moneysac.Model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Category implements Serializable, Comparable<Category> {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private SacEntryType type;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SacEntryType getType() {
		return type;
	}

	public void setType(SacEntryType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

	@Override
	public int compareTo(Category another) {
		return getId().compareTo(another.getId());
	}
}
