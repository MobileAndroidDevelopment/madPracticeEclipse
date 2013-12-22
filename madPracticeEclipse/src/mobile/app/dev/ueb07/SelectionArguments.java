package mobile.app.dev.ueb07;

public class SelectionArguments {
	private final String tableName;
	private String[] projection;
	private String selection;
	private String[] selectionArguments;
	private String sortOrder;

	public SelectionArguments(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public String[] getProjection() {
		return projection;
	}

	public SelectionArguments setProjection(String[] projection) {
		this.projection = projection;
		return this;
	}

	public String getSelection() {
		return selection;
	}

	/**
	 * fügt eine weitere Selektion hinzu
	 * @param selection
	 * @return this
	 */
	public SelectionArguments addSelection(String selection) {
		if (selection == null)
			this.selection = selection;
		else
			this.selection += " AND " + selection;
		return this;
	}

	public String[] getSelectionArguments() {
		return selectionArguments;
	}

	public SelectionArguments setSelectionArguments(String[] selectionArguments) {
		this.selectionArguments = selectionArguments;
		return this;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public SelectionArguments setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
		return this;
	}
}
