package mobile.app.dev.ueb06;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Priority {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;

	public int getId() {
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

	@Override
	public String toString() {
		return "Priority [id=" + id + ", name=" + name + "]";
	}

}
