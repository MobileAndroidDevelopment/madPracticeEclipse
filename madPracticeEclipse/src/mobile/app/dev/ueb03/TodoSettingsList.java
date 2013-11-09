package mobile.app.dev.ueb03;

import java.util.LinkedList;

public class TodoSettingsList extends LinkedList<Setting>{
	
	private static TodoSettingsList instance;

	public static synchronized TodoSettingsList getInstance() {
		if (instance == null) {
			instance = new TodoSettingsList();
		}
		return instance;
	}

	private TodoSettingsList() {
	}
}
