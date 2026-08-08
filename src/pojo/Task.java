package pojo;

import java.time.LocalDate;

public class Task {
	
	private Long id;
	private String title;
	private String description;
	private Priority priority;
	private LocalDate dueDate;
	private LocalDate createdDate;
	private Status status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Task(String title, String description, Priority priority, LocalDate dueDate, LocalDate createdDate,Status status) {
		super();
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate;
		this.createdDate=createdDate;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Task-id=" + id + "\n, title=" + title + "\n, description=" + description + "\n, priority=" + priority
				+ "\n, dueDate=" + dueDate + "\n, status=" + status;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
