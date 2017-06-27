package fr.imie.suptodo.dao;

import java.util.List;

import fr.imie.suptodo.model.User;

public interface UserDao {
	public abstract Long createUser(User user);
	public abstract User findUserById(Long id);
	public abstract boolean updateUser(User user);
	public abstract boolean removeUser(User user);
	public abstract List<User> getAllUsers();
}
