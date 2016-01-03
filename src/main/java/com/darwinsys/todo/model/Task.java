package com.darwinsys.todo.model;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.darwinsys.todo.database.DateConverter;
import com.darwinsys.todo.database.PriorityConverter;

/**
 * One ToDo item or "task".
 * See http://todotxt.com/ and 
 * https://github.com/ginatrapani/todo.txt-cli/wiki/The-Todo.txt-Format.
 * @author Ian Darwin
 */
@Entity
@Table(name="todo")
public class Task implements Serializable {
	
	private static final long serialVersionUID = 4917727200248757334L;
	
	private static final char PROJECT = '+', CONTEXT = '@';
	long id;
	Priority priority; // Enum; how important?
	String name;	// what to do
	Date creationDate = new Date(); // when you decided you had to do it
	Project project;	// what this task is part of
	Context context;	// where to do it
	Date dueDate;		// when to do it by
	Status status;
	Date completedDate = null; // when you actually did it
	long modified = System.currentTimeMillis();	// tstamp (UTC!) when last modified.
	String description;
	
	public Task() {
		super();
		creationDate = new Date();
	}
	
	public Task(String name) {
		this();
		this.name = name;
	}
	
	public Task(String name, String project, String context) {
		this.name = name;
		this.project = new Project(project);
		this.context = new Context(context);
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	//@Enumerated(EnumType.ORDINAL)
	@Convert(converter=PriorityConverter.class)
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Convert(converter=DateConverter.class)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@ManyToOne
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@ManyToOne
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Convert(converter=DateConverter.class)
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
	
	public void toggleComplete() {
		setStatus(!(status == Status.COMPLETE) ? Status.COMPLETE : Status.ACTIVE);
	}
	
	@Convert(converter=DateConverter.class)
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public void active() {
		this.completedDate = null;
		status = Status.ACTIVE;
	}
	
	public void complete() {
		this.completedDate = new Date();
		status = Status.COMPLETE;
	}
	
	final static char[] prioLetters = "ABCDEFG".toCharArray();
	
	/** ToString converts to String but in todo.txt format! 
	 * A fully-fleshed-out example from todotxt.com:
	 * x 2011-03-02 2011-03-01 Review Tim's pull request +TodoTxtTouch @github 
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (status == Status.COMPLETE) {
			sb.append('x');
			if (getCompletedDate() != null) {
				sb.append(' ').append(getCompletedDate()).append(' ');
			}
		}
		if (getPriority() != null) {
			sb.append('(').append(prioLetters[getPriority().ordinal()]).append(')').append(' ');
		}
		if (getCreationDate() != null) {
			sb.append(getCreationDate());
		}
		sb.append(' ').append(name);
		if (getProject() != null) {
			sb.append(' ').append(PROJECT).append(project);
		}
		if (getContext() != null) {
			sb.append(' ').append(CONTEXT).append(context);
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((completedDate == null) ? 0 : completedDate.hashCode());
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (modified ^ (modified >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id != other.id)
			return false;
		if (modified != other.modified)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority != other.priority)
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (status != other.status)
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
