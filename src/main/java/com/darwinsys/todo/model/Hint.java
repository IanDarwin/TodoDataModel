package com.darwinsys.todo.model;

import java.io.Serializable;

import jakarta.persistence.*;

/**
 * A Hint is one little note to help people improve their use of
 * ToDo list management.
 */
@Entity
public class Hint implements Serializable {

	private static final long serialVersionUID = 8438161346456329618L;
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
