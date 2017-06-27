package fr.imie.suptodo.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import fr.imie.suptodo.dao.DaoFactory;
import fr.imie.suptodo.dao.ToDoDao;
import fr.imie.suptodo.model.ToDo;

@SuppressWarnings("serial")
public class WriteToDoFrame extends JFrame {
	
	private JTextArea todoText;
	private JButton addTodo;
	private ToDoDao toDoDao;
	
	public WriteToDoFrame() {
		init();
	}

	private void init() {
		JPanel rootPane = new JPanel(new BorderLayout());
		JPanel mainField = new JPanel(new GridBagLayout());
		JLabel title = new JLabel("Write new Todo", SwingConstants.CENTER);
		
		rootPane.add(title, BorderLayout.NORTH);
		
		todoText = new JTextArea(4, 2);
		todoText.setText("Enter new Todo...");
		todoText.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				todoText.setText("");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {				
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      
		c.weightx = 1;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		mainField.add(todoText, c);
		
		addTodo = new JButton("Add todo");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       
		c.weightx = 0;
		c.weighty = 0.0;   
		c.anchor = GridBagConstraints.PAGE_END; 
		c.insets = new Insets(20,200,0,0);  
		c.gridx = 0;       
		c.gridwidth = 1;   
		c.gridy = 2;
		
		mainField.add(addTodo, c);
		
		rootPane.add(mainField, BorderLayout.CENTER);
		
		initListeners();
		
		this.setTitle("SUPTODO");
		this.setSize(500, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(rootPane);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initListeners() {
		addTodo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = todoText.getText();
				toDoDao = DaoFactory.getToDoDao();
				
				ToDo myToDo = new ToDo();
				myToDo.setDescription(text);
				myToDo.setDate(new Date());
				
				Long id = toDoDao.createToDo(myToDo);
				
				JOptionPane.showConfirmDialog(WriteToDoFrame.this, "A new todo has been created with ID : " + id, "Message", JOptionPane.DEFAULT_OPTION);
				WriteToDoFrame.this.dispose();
			}
		});
		
	}
}
