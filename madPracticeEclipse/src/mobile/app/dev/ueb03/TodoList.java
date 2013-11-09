package mobile.app.dev.ueb03;

import java.util.LinkedList;

/**
 * Singleton-Implementierung der Todo-Liste
 * @author Benne
 */
public class TodoList extends LinkedList<Todo> {

	private static TodoList instance;

	public static synchronized TodoList getInstance() {
		if (instance == null) {
			instance = new TodoList();
		}
		return instance;
	}

	private TodoList() {
	}

	public void overrideExisting(Todo todo) {
		for (Todo nextTodo : this) {
			if (nextTodo.getiD() == todo.getiD()) {
				nextTodo.setValues(todo);
			}
		}
	}
}
