package com.accenture.letovit.todolist;

import java.io.Serializable;

public class ToDoItem implements Serializable{
	private String title; //must be exactly called "title"
	private String text; //must be exactly called "text"
	private boolean finished; //must be exactly called "finished"
	private String createdAt; //must be exactly called "createdAt"
	
	// Generated
	@Override
	public String toString() {
		return "ToDoItem [title=" + title + ", text=" + text + ", finished=" + finished + ", createdAt=" + createdAt
				+ "]";
	}
	// Generated
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
	
}
