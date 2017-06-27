package fr.imie.suptodo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import fr.imie.suptodo.dao.CommentDao;
import fr.imie.suptodo.dao.DaoFactory;
import fr.imie.suptodo.dao.ToDoDao;
import fr.imie.suptodo.model.Comment;
import fr.imie.suptodo.model.ToDo;

public class JdbcToDoDao extends JdbcDao implements ToDoDao {

	public JdbcToDoDao(Connection connection) {
		super(connection);
	}

	@Override
	public Long createToDo(ToDo todo) {
		String query = "INSERT INTO todo SET dateToDo=?, description=?, isDone=?;";
		
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, todo.getDate().getTime());
			pstmt.setString(2, todo.getDescription());
			pstmt.setBoolean(3, todo.isDone());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			Long id = rs.getLong(1);
			
			return id;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ToDo findToDoById(Long id) {
		String query = "SELECT * FROM todo WHERE id=?;";
		
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query);
			pstmt.setLong(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			ToDo toDo = new ToDo();
			
			if (rs.next()) {
				
				toDo.setId(rs.getLong(1));
				toDo.setDate(new Date(rs.getLong(2)));
				toDo.setDescription(rs.getString(3));
				toDo.setDone(rs.getBoolean(4));
			}
			
			return toDo;
			
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Unable to find the toDo with ID : " + id);
		}
		
	}

	@Override
	public boolean updateToDo(ToDo todo) {
		String query = "UPDATE todo SET isDone = ? WHERE id = ?";
		
		try {
			PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setBoolean(1, todo.isDone());
            statement.setLong(2, todo.getId());
            boolean result = statement.executeUpdate() > 0;
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update this todo: " + todo.getDescription(), e);
        }
	}

	@Override
	public boolean removeToDo(ToDo todo) {
		String query = "DELETE FROM todo WHERE id = ?";

		try {
			PreparedStatement statement = getConnection().prepareStatement(query);
			statement.setLong(1, todo.getId());
			boolean result = statement.executeUpdate() > 0;
			return result;
		} catch (SQLException e) {
			throw new RuntimeException("Unable to remove this todo: " + todo.getDescription(), e);
		}
	}

	@Override
	public List<ToDo> getAllToDo() {
		String query = "SELECT * FROM todo";

		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			List<ToDo> listToDo = new ArrayList<>();
			
			CommentDao commentDao = DaoFactory.getCommentDao();
			
			List<Comment> listComment = new ArrayList<>();
			
			while (rs.next()) {
			
				listComment = commentDao.getAllCommentsByToDoId(rs.getLong(1));
			
			
				ToDo myToDo = new ToDo();
				myToDo.setId(rs.getLong(1));
				myToDo.setDate(rs.getDate(2));
				myToDo.setDescription(rs.getString(3));
				myToDo.setDone(rs.getBoolean(4));
				
				myToDo.setComments(listComment);
				
				listToDo.add(myToDo);
			}
			
			return listToDo;
			
		} catch (SQLException e) {
			throw new RuntimeException("Unable to get all the todo: " + e);
		}
	}

}
