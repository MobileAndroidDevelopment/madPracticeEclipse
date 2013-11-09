package mobile.app.dev.ueb03;

public enum Priority {

	hoch(0),mittel(1),niedrig(2);
	
	private Priority(int position){
		this.position = position;
	}
	
	/** Position innerhalb der Auswahl */
	private final int position;

	public int getPosition() {
		return position;
	}
}
