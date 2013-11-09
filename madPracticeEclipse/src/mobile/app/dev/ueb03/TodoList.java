package mobile.app.dev.ueb03;

import java.util.LinkedList;

public class TodoList extends LinkedList<Todo>{

	private static TodoList instance;
	
	public static synchronized TodoList getInstance(){
		if(instance == null){
			instance = new TodoList();
		} 
		return instance;
	}
	
	private TodoList(){
		
	}
}
