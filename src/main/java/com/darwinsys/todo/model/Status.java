package com.darwinsys.todo.model;

/** Enumerate the status that a ToDo can be in. */
public enum Status {
	NEW,
	ACTIVE,
	DEFERRED,
	COMPLETE;
	static final Status DEFAULT = NEW;
}
