package fr.imie.suptodo.dao;

import java.util.List;

import fr.imie.suptodo.model.Comment;

public interface CommentDao {
	public abstract Long createComment(Comment c);
	public abstract List<Comment> getAllComments();
	public abstract boolean removeComment(Comment c);
	List<Comment> getAllCommentsByToDoId(Long id);
}
