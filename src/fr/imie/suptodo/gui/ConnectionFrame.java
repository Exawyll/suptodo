package fr.imie.suptodo.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;

import fr.imie.suptodo.dao.DaoFactory;
import fr.imie.suptodo.dao.UserDao;
import fr.imie.suptodo.launcher.GuiLauncher;
import fr.imie.suptodo.model.User;
import fr.imie.suptodo.utils.BCrypt;

@SuppressWarnings("serial")
public class ConnectionFrame extends JFrame {
	
	private JTextField userName;
	private JPasswordField password;
	private JButton login;
	private UserDao userDao;
	
	public ConnectionFrame() {
		userDao = DaoFactory.getUserDao();
		init();
	}

	private void init() {
		JPanel rootPane = new JPanel(new BorderLayout());
		JPanel textFields = new JPanel(new GridBagLayout());
		JLabel title = new JLabel("Login to SUPtodo", SwingConstants.CENTER);
		GridBagConstraints c = new GridBagConstraints();
		rootPane.add(title, BorderLayout.NORTH);
		
		userName = new JTextField();
		userName.setText("Username...");
		userName.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				userName.setText("");
				
			}
		});
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 1;
		textFields.add(userName, c);
		
		password = new JPasswordField();
		password.setText("Password...");
		password.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				password.setText("");

			}
		});
		
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 3;
		textFields.add(password, c);
		
		login = new JButton("login");
		login.setLayout(new FlowLayout());
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 4;
		textFields.add(login, c);
		
		rootPane.add(textFields, BorderLayout.SOUTH);
		
		initListeners();
		
		this.setTitle("SUPTODO");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setContentPane(rootPane);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initListeners() {
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = userName.getText();
				char[] userPassword = password.getPassword();
				
				String myString = String.valueOf(userPassword);
				
				List<User> listUser = new ArrayList<User>();
				listUser = userDao.getAllUsers();
				boolean flag = false;
				
				ListIterator<User> itUser = listUser.listIterator();
				while (itUser.hasNext()) {
					User myUser = itUser.next();
					String mdp = myUser.getPassword();
															
					boolean matched = BCrypt.checkpw(myString, mdp);
					
					if (myUser.getName().equals(name) && matched) {
						JOptionPane.showConfirmDialog(ConnectionFrame.this, "Welcome " + name, "Message", JOptionPane.DEFAULT_OPTION);
						
						if (myUser.isManager()) {
							AllToDoFrame.isManager = true;
						}
						
						GuiLauncher.currentUserId = myUser.getId();
						GuiLauncher.currentUser = myUser;
						
						new AllToDoFrame();
						ConnectionFrame.this.dispose();
							
						flag = true;
						break;
					} else {
						flag = false;
					}
				}
				if (!flag) {
					JOptionPane.showConfirmDialog(ConnectionFrame.this, "Problem with login or password", "Message", JOptionPane.DEFAULT_OPTION);
				}
			}
		});
	}
}
