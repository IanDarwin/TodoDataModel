package com.darwinsys.todo.model;

import javax.persistence.*;

@Entity
public class Project {
	long id;
	String name;

	public Project() {
		// empty
	}

	public Project(String name) {
		this.name = name;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
