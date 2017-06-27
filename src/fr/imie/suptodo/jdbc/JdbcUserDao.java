package fr.imie.suptodo.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import fr.imie.suptodo.dao.UserDao;
import fr.imie.suptodo.model.Employe;
import fr.imie.suptodo.model.Manager;
import fr.imie.suptodo.model.User;

public class JdbcUserDao extends JdbcDao implements UserDao {

	public JdbcUserDao(Connection connection) {
		super(connection);
	}

	@Override
	public Long createUser(User user) {
		String query = "INSERT INTO user SET name=?, password=?, isManager=?;";
		
		try {
			// Set the prepared statement with the query
			PreparedStatement pstmt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setBoolean(3, user.isManager());
			
			// Execute the query
			pstmt.executeUpdate();
			
			// Get the generated key
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			Long id = rs.getLong(1);
			
			pstmt.close();
			
			// return the generated key
			return id;
			
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		
		return null;
	}

	@Override
	public User findUserById(Long id) {
		String query = "SELECT * FROM user WHERE id=?;";
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query);
			pstmt.setLong(1, id);
			
			// Get the result of the query in a result set
			ResultSet rs = pstmt.executeQuery();
			
			// Explore result set
			if (rs.next()) {
				User myUser;
				if (rs.getBoolean(4)) {
					myUser = new Manager();
				} else {
					myUser = new Employe();
				}
				myUser.setId(rs.getLong(1));
				myUser.setName(rs.getString(2));
				myUser.setPassword(rs.getString(3));
				
				return myUser;
				
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		String query = "UPDATE user SET id=?, name=?, password=?, isManager=? WHERE id=?";
		
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query);
			pstmt.setLong(1, user.getId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPassword());
			pstmt.setBoolean(4, user.isManager());
			pstmt.setLong(5, user.getId());
			
			pstmt.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
				
	}

	@Override
	public boolean removeUser(User user) {
		String query = "DELETE FROM user WHERE id=?;";
		
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query);
			pstmt.setLong(1, user.getId());
			
			pstmt.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			throw new RuntimeErrorException(null, "The specified user cannot be removed");
		}
	
	}

	@Override
	public List<User> getAllUsers() {
		String query = "SELECT * FROM user;";
		
		try {
			PreparedStatement pstmt = getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			List<User> myUsers = new ArrayList<>();
			
			while (rs.next()) {
				User user;
				if (rs.getBoolean(4)) {
					user = new Manager();
				} else {
					user = new Employe();
				}
				
				user.setId(rs.getLong(1));
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				
				myUsers.add(user);
			}
			
			return myUsers;
		
		} catch (Exception e) {
			throw new RuntimeErrorException(null, "Cannot get the list of users");
		}
	}

}
