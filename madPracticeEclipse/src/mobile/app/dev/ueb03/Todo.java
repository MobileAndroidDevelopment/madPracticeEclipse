package mobile.app.dev.ueb03;

import java.io.Serializable;

public class Todo implements Serializable{
	private static int identifier = 0;
	
	private int iD;
	private String title;
	private String desc;
	private String priority;

	public Todo(String title, String desc, String priority) {
		this.iD = identifier++;
		this.title = title;
		this.desc = desc;
		this.priority = priority;
	}

	public int getiD() {
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

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Todo [title=" + title + ", desc=" + desc + ", priority=" + priority + "]";
	}

}
