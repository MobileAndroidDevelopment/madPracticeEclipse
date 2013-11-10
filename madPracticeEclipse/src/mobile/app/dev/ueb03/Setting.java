package mobile.app.dev.ueb03;

import java.io.Serializable;

public class Setting implements Serializable, Comparable<Setting> {
	private static int identifier = 0;
	private String settingTitle;
	private int iD;
	
	public Setting(String settingTitle) {
		this.iD = identifier++;
		this.settingTitle = settingTitle;		
	}
	
	public Integer getiD() {
		return iD;
	}
	
	public String getTitle() {		
		return settingTitle;
	}	
	
	@Override
	public int compareTo(Setting another) {
		return getiD().compareTo(another.getiD());
	}
}
