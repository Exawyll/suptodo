package fr.imie.suptodo.dao;

import fr.imie.suptodo.jdbc.JdbcCommentDao;
import fr.imie.suptodo.jdbc.JdbcToDoDao;
import fr.imie.suptodo.jdbc.JdbcUserDao;
import fr.imie.suptodo.utils.ConnectionManager;


public class DaoFactory {
	
	public DaoFactory() {
		
	}

	public static CommentDao getCommentDao() {
		return new JdbcCommentDao(ConnectionManager.connectDB());
	}

	public static ToDoDao getToDoDao() {
		return new JdbcToDoDao(ConnectionManager.connectDB());
	}
	
	public static UserDao getUserDao() {
		return new JdbcUserDao(ConnectionManager.connectDB());
	}
}
