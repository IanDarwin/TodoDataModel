package com.darwinsys.todo.model;

import javax.persistence.*;

/**
 * A Hint is one little note to help people improve their use of
 * ToDo list management.
 */
@Entity
public class Hint {
	long id;
	String hint;
	String author;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		if (author == null || author.length() == 0)
			return getHint();
		else
			return getHint() + " -- " + getAuthor();
	}
}
