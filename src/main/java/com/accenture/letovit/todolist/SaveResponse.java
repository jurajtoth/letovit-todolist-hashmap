package com.accenture.letovit.todolist;

public class SaveResponse {
	private String name; // must be called "name"

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SaveResponse [name=" + name + "]";
	}
	

}
