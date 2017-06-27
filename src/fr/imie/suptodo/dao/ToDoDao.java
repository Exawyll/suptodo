package fr.imie.suptodo.dao;

import java.util.List;

import fr.imie.suptodo.model.ToDo;

public interface ToDoDao {
	public abstract Long createToDo (ToDo todo);
	public abstract ToDo findToDoById (Long id);
	public abstract boolean updateToDo (ToDo todo);
	public abstract boolean removeToDo (ToDo todo);
	public abstract List<ToDo> getAllToDo ();
}
