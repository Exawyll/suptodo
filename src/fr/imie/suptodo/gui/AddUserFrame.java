package fr.imie.suptodo.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;

import fr.imie.suptodo.dao.DaoFactory;
import fr.imie.suptodo.dao.UserDao;
import fr.imie.suptodo.model.Employe;
import fr.imie.suptodo.model.Manager;
import fr.imie.suptodo.model.User;
import fr.imie.suptodo.utils.BCrypt;

@SuppressWarnings("serial")
public class AddUserFrame extends JFrame {
	
	private JPanel panelName;
	private JPanel passwordPanel;
	private JPanel isManagerPanel;
	private JTextField name;
	private JPasswordField password;
	private JCheckBox isManagerBox;
	private JPanel buttonPane;
	private JButton addUser;
	private UserDao userDao;

	public AddUserFrame() {
		
		JPanel container = new JPanel(new BorderLayout());
		
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		panelName = new JPanel(new FlowLayout());
		JLabel labelName = new JLabel("Name: ");
		name = new JTextField(10);
		labelName.setLabelFor(name);
		panelName.add(labelName);
		panelName.add(name);

		passwordPanel = new JPanel(new FlowLayout());
		JLabel labelPassword = new JLabel("Password: ");
		password = new JPasswordField(10);
		labelPassword.setLabelFor(password);
		passwordPanel.add(labelPassword);
		passwordPanel.add(password);
		
		isManagerPanel = new JPanel(new FlowLayout());
		isManagerBox = new JCheckBox("Manager ?");
		isManagerPanel.add(isManagerBox);
		
		c.gridy = 0;
		p.add(panelName, c);
		c.gridy = 1;
		p.add(passwordPanel, c);
		c.gridy = 2;
		p.add(isManagerPanel, c);
        
        buttonPane = new JPanel();
        addUser = new JButton("Add User");
        buttonPane.add(addUser);
        
        container.add(p, BorderLayout.NORTH);
        container.add(buttonPane, BorderLayout.SOUTH);

        initListeners ();
        this.setTitle("Add User");
		this.setSize(400, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(container);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	private void initListeners() {
		addUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = name.getText();
				char[] userPassword = password.getPassword();
				
				String myPassword = String.valueOf(userPassword);
		        String generatedSecuredPasswordHash = BCrypt.hashpw(myPassword, BCrypt.gensalt(12));
		        boolean flag = false;
				
		        List<User> listUsers = new ArrayList<>();
		        ListIterator<User> itUser = listUsers.listIterator();
		        
		        while (itUser.hasNext()) {
		        	User user = itUser.next();
		        	
		        	if (user.getName().equals(userName)) {
		        		flag = true;
		        	}
		        }
		        
		        if (flag) {
		        	JOptionPane.showConfirmDialog(AddUserFrame.this, "User name already exist in database", "Message", JOptionPane.DEFAULT_OPTION);
		        } else {
		        	boolean isManager = isManagerBox.isSelected();
					
					userDao = DaoFactory.getUserDao();
					User myUser;
					
					if (isManager) {
						myUser = new Manager();
					} else {
						myUser = new Employe();
					}
					
					myUser.setName(userName);
					myUser.setPassword(generatedSecuredPasswordHash);
					
					Long id = userDao.createUser(myUser);
					JOptionPane.showConfirmDialog(AddUserFrame.this, "User added into DB with ID: " + id, "Message", JOptionPane.DEFAULT_OPTION);
					AddUserFrame.this.dispose();
		        }
			}
		});
		
	}
}
