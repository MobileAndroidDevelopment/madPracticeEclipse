package mobile.app.dev.ueb06.orm;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Category implements Serializable{

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;

	/** leerer Konstruktor, wichtig fuer ORM */
	public Category() {
	}

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
		return name + " (" + id + ")";
	}

}
