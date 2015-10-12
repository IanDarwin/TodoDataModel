package com.darwinsys.todo.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/** One ToDo item or "task".
 * See http://todotxt.com/ and https://github.com/ginatrapani/todo.txt-cli/wiki/The-Todo.txt-Format.
 * @author Ian Darwin
 */
@Entity
public class Task implements Serializable {
	
	private static final long serialVersionUID = 4917727200248757334L;
	
	private static final char SPACE = ' ';
	private static final Date today = new Date();

	private static final char PROJECT = '+', CONTEXT = '@';
	long id;
	Character priority; // 'A'..'Z': how important?
	String name;	// what to do
	Date creationDate; // when you decided you had to do it
	Project project;		// what this task is part of
	Context context;	// where to do it
	Date dueDate;	// when to do it by
	Status status;
	Date completedDate; // when you actually did it
	long modified = System.currentTimeMillis();	// tstamp (UTC!) when last modified.
	
	public Task() {
		super();
		creationDate = new Date();
	}
	
	public Task(String name) {
		this();
		this.name = name;
	}
	
	public Task(String name, String project, String context) {
		this(0, null, name, today, project, context, false, null, null);
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Character getPriority() {
		return priority;
	}
	public void setPriority(Character priority) {
		this.priority = priority;
	}
	public void setPriority(char priority) {
		if (priority < 'A' || priority > 'Z') {
			throw new IllegalArgumentException("Invalid priority " + priority);
		}
		setPriority(Character.valueOf(priority));
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="year",column=@Column(name="createdYear")),
	    @AttributeOverride(name="month",column=@Column(name="createdMonth")),
	    @AttributeOverride(name="day",column=@Column(name="createdDay")),
	  })
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="year",column=@Column(name="dueYear")),
	    @AttributeOverride(name="month",column=@Column(name="dueMonth")),
	    @AttributeOverride(name="day",column=@Column(name="dueDay")),
	  })
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Status getStatus() {
		return status;
	}

	/**
	 * set completion to true or false, and side-effect set completion date
	 * to today or to null, but if setting to true and client already set
	 * completion data, leave well enough alone.
	 * XXX This may not play well with JPA
	 * @param complete
	 */
	public void setStatus(Status status) {
		this.status = status;
		if (status == Status.COMPLETE) {
			if (getCompletedDate() == null) {
				completedDate = new Date();
			}
		} else {
			setCompletedDate(null);
		}
	}
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="year",column=@Column(name="completedYear")),
	    @AttributeOverride(name="month",column=@Column(name="completedMonth")),
	    @AttributeOverride(name="day",column=@Column(name="completedDay")),
	  })
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
		complete = completedDate != null;
	}
	
	/** ToString converts to String but in todo.txt format! 
	 * A fully-fleshed-out example from todotxt.com:
	 * x 2011-03-02 2011-03-01 Review Tim's pull request +TodoTxtTouch @github 
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isComplete()) {
			sb.append('x').append(SPACE).append(getCompletedDate()).append(SPACE);
		}
		sb.append(getCreationDate());
		sb.append(SPACE);
		sb.append(name);
		if (getProject() != null) {
			sb.append(SPACE).append(PROJECT).append(project);
		}
		if (getContext() != null) {
			sb.append(SPACE).append(CONTEXT).append(context);
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result
				+ ((completedDate == null) ? 0 : completedDate.hashCode());
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (complete != other.complete)
			return false;
		if (completedDate == null) {
			if (other.completedDate != null)
				return false;
		} else if (!completedDate.equals(other.completedDate))
			return false;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}
}
