package fr.imie.suptodo.model;

public class Comment {

	private Long id;
	private String text;
	private User author;
	private ToDo toDo;
	
	public Comment() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public ToDo getToDo() {
		return toDo;
	}

	public void setToDo(ToDo toDo) {
		this.toDo = toDo;
	}

	@Override
	public String toString() {
		return "Comment " + id + ", text=" + text + ", author=" + author.getName() + ", toDo=" + toDo.getId();
	}
	
	
}
