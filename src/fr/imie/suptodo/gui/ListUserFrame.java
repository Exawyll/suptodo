package fr.imie.suptodo.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import fr.imie.suptodo.dao.DaoFactory;
import fr.imie.suptodo.dao.UserDao;
import fr.imie.suptodo.gui.model.ListUserTableModel;
import fr.imie.suptodo.model.User;

@SuppressWarnings("serial")
public class ListUserFrame extends JFrame {
	
	private UserDao userDao;
	private List<User> allUsers;
	
	public ListUserFrame() {
		userDao = DaoFactory.getUserDao();
		allUsers = new ArrayList<>();
		allUsers = userDao.getAllUsers();
		
		if (!allUsers.isEmpty()) {
        	init();
        } else {
        	JOptionPane.showConfirmDialog(ListUserFrame.this, "No user registered !", "Message", JOptionPane.DEFAULT_OPTION);
        }
	}

	private void init() {

		JPanel container = new JPanel(new BorderLayout());
		
		ListUserTableModel userModel = new ListUserTableModel(allUsers);
		JTable userTable = new JTable(userModel);
		JScrollPane userScrollPane = new JScrollPane(userTable);
		
		container.add(userScrollPane, BorderLayout.CENTER);
		
		this.setTitle("List of users");
		this.setSize(400, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(container);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}
	
}
