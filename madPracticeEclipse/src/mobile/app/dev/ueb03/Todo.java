package mobile.app.dev.ueb03;

public class Todo {

	private String title;
	private String remark;
	private String priority;

	public Todo(String title, String remark, String priority) {
		this.title = title;
		this.remark = remark;
		this.priority = priority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Todo [title=" + title + ", remark=" + remark + ", priority=" + priority + "]";
	}

}
