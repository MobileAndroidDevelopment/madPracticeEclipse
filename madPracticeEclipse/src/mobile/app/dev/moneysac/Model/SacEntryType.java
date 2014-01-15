package mobile.app.dev.moneysac.Model;

import java.io.Serializable;
import java.util.Comparator;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SacEntryType implements Serializable, Comparable<SacEntryType> {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(unique = true)
	private String name;

	public SacEntryType() {
	}

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

	@Override
	public String toString() {
		return "SacEntryType [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int compareTo(SacEntryType another) {
		return getId().compareTo(another.getId());
	}

	public static Comparator<SacEntryType> getNameComparator() {
		return new SacEntryTypeNameComparator();
	}

	private static class SacEntryTypeNameComparator implements Comparator<SacEntryType> {

		@Override
		public int compare(SacEntryType arg0, SacEntryType arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}

	}

}
