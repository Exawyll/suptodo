package fr.imie.suptodo.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import fr.imie.suptodo.dao.CommentDao;
import fr.imie.suptodo.dao.DaoFactory;
import fr.imie.suptodo.dao.ToDoDao;
import fr.imie.suptodo.dao.UserDao;
import fr.imie.suptodo.gui.model.CommentTableModel;
import fr.imie.suptodo.launcher.GuiLauncher;
import fr.imie.suptodo.model.Comment;
import fr.imie.suptodo.model.ToDo;
import fr.imie.suptodo.model.User;

@SuppressWarnings("serial")
public class AllToDoFrame extends JFrame {
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem addUser, addToDo, quit;

	private ToDoDao toDoDao;
	private UserDao userDao;
	private CommentDao commentDao;
	private List<ToDo> listToDo;
	private JTabbedPane tabbedPane;
	private JCheckBox isDone;
	private JTextArea comment;
	private JLabel title;
	private JLabel description;
	private JPanel rootPane;
	private JButton save;
	private JTable commentTable;
	private JScrollPane pane;
	private JPanel container;
	public static boolean isManager = false;
	private CommentTableModel commentModel;
	private List<Comment> currentComments;
    
    public AllToDoFrame () {
    	toDoDao = DaoFactory.getToDoDao();
    	commentDao = DaoFactory.getCommentDao();
    	listToDo = new ArrayList<>();
        listToDo = toDoDao.getAllToDo();
        if (!listToDo.isEmpty()) {
        	init();
        } else {
        	JOptionPane.showConfirmDialog(AllToDoFrame.this, "Nothing to do right now !", "Message", JOptionPane.DEFAULT_OPTION);
        }
    }
    
    private void init() {
    	
    	if (GuiLauncher.currentUser.isManager()) {
    		buildMenu();
    	}

    	container = new JPanel(new BorderLayout());
    	tabbedPane = new JTabbedPane();
        
        for (int i = 0, n = listToDo.size(); i < n; i++) {
        	add(tabbedPane, "Ticket " + listToDo.get(i).getId(), listToDo.get(i));
        }
        
    	save = new JButton("Save");
        
        container.add(tabbedPane, BorderLayout.CENTER);
        container.add(save, BorderLayout.SOUTH);
        
        initListeners();
        this.setTitle("SUPTODO");
		this.setSize(500, 400);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(container);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
    }
    
    private void add(JTabbedPane tabbedPane, String label, ToDo toDo) {
    	
    	rootPane = new JPanel(new GridBagLayout());
    	
    	GridBagConstraints c = new GridBagConstraints();
    	title = new JLabel("Title: " + label);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	rootPane.add(title);

    	JLabel descriptionLabel = new JLabel("Description: ");
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1;
    	c.gridx = 0;
    	c.gridy = 1;
    	rootPane.add(descriptionLabel, c);

    	description = new JLabel(toDo.getDescription());
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1;
    	c.gridx = 0;
    	c.gridy = 2;
    	rootPane.add(description, c);

    	isDone = new JCheckBox("Mark as done");
    	isDone.setSelected(toDo.isDone());
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1;
    	c.gridx = 0;
    	c.gridy = 3;
    	rootPane.add(isDone, c, 1);

    	JLabel commentLabel = new JLabel("Comment:");
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1;
    	c.gridx = 0;
    	c.gridy = 4;
    	rootPane.add(commentLabel, c);

    	comment = new JTextArea();
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1;
    	c.gridx = 0;
    	c.gridy = 5;
    	rootPane.add(comment, c, 2);

    	currentComments = commentDao.getAllCommentsByToDoId(toDo.getId());
    	commentModel = new CommentTableModel(currentComments);
    	commentTable = new JTable(commentModel);
    	pane = new JScrollPane(commentTable);
    	commentTable.setAutoCreateRowSorter(true);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.weightx = 1;
    	c.gridx = 0;
    	c.gridy = 6;
    	c.ipady = 100;
    	rootPane.add(pane, c);
    	
    	tabbedPane.addTab(label, rootPane);
    }

	private void initListeners() {
				
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = tabbedPane.getSelectedIndex();
				rootPane = (JPanel) tabbedPane.getSelectedComponent();
				isDone = (JCheckBox) rootPane.getComponent(1);
				comment = (JTextArea) rootPane.getComponent(2);
								
				if (comment.getText().isEmpty() && isDone.isSelected()) {
					JOptionPane.showConfirmDialog(AllToDoFrame.this, "You need to comment before check is done !", "Message", JOptionPane.DEFAULT_OPTION);
				} else {
					
					ToDo currentTodo = new ToDo();
					currentTodo = listToDo.get(index);
					currentTodo.setDone(isDone.isSelected());
					Comment newComment = null;
					
					if (!comment.getText().isEmpty()) {
						
						userDao = DaoFactory.getUserDao();
						commentDao = DaoFactory.getCommentDao();
						
						User currentUser = userDao.findUserById(GuiLauncher.currentUserId);

						newComment = new Comment();
						newComment.setId((long) 0);
						newComment.setAuthor(currentUser);
						newComment.setText(comment.getText());
						newComment.setToDo(currentTodo);
						currentComments.add(newComment);

						newComment.setId(commentDao.createComment(newComment));
					}
					
					toDoDao.updateToDo(currentTodo);
					commentModel.fireTableDataChanged();
				}
			}
		});
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				GuiLauncher.currentUser = null;
				GuiLauncher.currentUserId = null;
				new ConnectionFrame();
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
		});

		
	}

	private void buildMenu() {
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		addUser = new JMenuItem("Add user");
		JMenuItem listUser = new JMenuItem("List users");
		addToDo = new JMenuItem("Add todo");
		quit = new JMenuItem("Quit");

		menu.add(addUser);
		menu.add(listUser);
		menu.add(addToDo);
		menu.add(quit);

		addUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddUserFrame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		listUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ListUserFrame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		addToDo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new WriteToDoFrame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AllToDoFrame.this.dispose();
				GuiLauncher.currentUser = null;
				GuiLauncher.currentUserId = null;
				new ConnectionFrame();

			}
		});
		this.setJMenuBar(menuBar);
	}
}

