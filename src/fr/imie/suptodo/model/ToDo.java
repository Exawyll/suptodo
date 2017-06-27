package fr.imie.suptodo.model;

import java.util.Date;
import java.util.List;

public class ToDo {
	
	private Long id;
	private String description;
	private Date date;
	private List<Comment> comments;
	private boolean isDone = false;
	
	public ToDo() {
		this.setDate(new Date());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ToDo [id=" + id + ", description=" + description + ", date=" + date + ", comments=" + comments
				+ ", isDone=" + isDone + "]";
	}	

	
}
