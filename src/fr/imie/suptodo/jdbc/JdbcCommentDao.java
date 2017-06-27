package fr.imie.suptodo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import com.mysql.jdbc.Statement;

import fr.imie.suptodo.dao.CommentDao;
import fr.imie.suptodo.dao.DaoFactory;
import fr.imie.suptodo.dao.ToDoDao;
import fr.imie.suptodo.dao.UserDao;
import fr.imie.suptodo.model.Comment;
import fr.imie.suptodo.model.ToDo;
import fr.imie.suptodo.model.User;

public class JdbcCommentDao extends JdbcDao implements CommentDao {

	public JdbcCommentDao(Connection connection) {
		super(connection);
	}

	@Override
	public Long createComment(Comment c) {
		String query = "INSERT INTO comment SET content=?, id_todo=?, id_user=?;";
		
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, c.getText());
			pstmt.setLong(2, c.getToDo().getId());
			pstmt.setLong(3, c.getAuthor().getId());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			
			Long id = rs.getLong(1);
			
			return id;
			
		} catch (SQLException e) {
			throw new RuntimeErrorException(null, "Unable to create a new comment");
		}
	}

	@Override
	public List<Comment> getAllCommentsByToDoId(Long id) {
		String query = "SELECT * FROM comment WHERE id_todo=?";
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query);
			pstmt.setLong(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			List<Comment> listComment = new ArrayList<>();
			UserDao userDao = DaoFactory.getUserDao();
			ToDoDao toDoDao = DaoFactory.getToDoDao();
			
			while (rs.next()) {
				Comment myComment = new Comment();
				myComment.setId(rs.getLong(1));
				myComment.setText(rs.getString(2));
				
				ToDo myToDo = toDoDao.findToDoById(id);
				myComment.setToDo(myToDo);
				
				User myUser = userDao.findUserById(rs.getLong(4));
				myComment.setAuthor(myUser);

				listComment.add(myComment);
			}
			
			return listComment;
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Unable to get all comments");
		}
	}

	@Override
	public boolean removeComment(Comment c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Comment> getAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

}
