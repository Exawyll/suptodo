package fr.imie.suptodo.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import fr.imie.suptodo.model.User;

@SuppressWarnings("serial")
public class ListUserTableModel extends AbstractTableModel {
	
	private List<User> allUsers;
	
	public ListUserTableModel(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	@Override
	public int getRowCount() {
		return allUsers.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = allUsers.get(rowIndex);
		if (columnIndex == 0) {
			return user.getId();
		} else if (columnIndex == 1) {
			return user.getName();
		} else if (columnIndex == 2) {
			return user.isManager();
		} else {
			return null;
		}
		
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "ID";
		} else if (column == 1) {
			return "Name";
		} else if (column == 2) {
			return "isManager";
		} else {
			return "No Name";
		}
	}

	
}
