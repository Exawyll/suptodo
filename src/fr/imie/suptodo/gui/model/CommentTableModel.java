package fr.imie.suptodo.gui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import fr.imie.suptodo.model.Comment;

@SuppressWarnings("serial")
public class CommentTableModel extends AbstractTableModel {
	
	private List<Comment> comments;
	
	public CommentTableModel(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int getRowCount() {
		return comments.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Comment c = comments.get(rowIndex);
		if (columnIndex == 0) {
			return c.getAuthor().getName();
		} else if (columnIndex == 1) {
			return c.getText();
		} else {
			return null;
		}
		
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Author";
		} else if (column == 1) {
			return "content";
		} else {
			return "No name";
		}
	}

}
