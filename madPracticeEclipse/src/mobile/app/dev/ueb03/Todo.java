package mobile.app.dev.ueb03;

import java.io.Serializable;

public class Todo implements Serializable, Comparable<Todo> {

	/** hochzaehlbare ID fuer die Generierung der Identifier */
	private static int identifier = 0;

	private int iD;
	private String title;
	private String desc;
	private Priority priority;

	public Todo(String title, String desc, Priority priority) {
		this.iD = identifier++;
		this.title = title;
		this.desc = desc;
		this.priority = priority;
	}

	public Integer getiD() {
		return iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Todo [title=" + title + ", desc=" + desc + ", priority=" + priority + "]";
	}

	/**
	 * Ueberschreibt die Werte mit Werten aus einem uebergebenen Todo. Die ID wird dabei nicht angeruehrt.
	 * @param todo
	 */
	public void setValues(Todo todo) {
		this.title = todo.title;
		this.desc = todo.desc;
		this.priority = todo.priority;
	}

	@Override
	public int compareTo(Todo another) {
		return getiD().compareTo(another.getiD());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + iD;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (iD != other.iD)
			return false;
		return true;
	}
}
