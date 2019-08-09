package com.accenture.letovit.todolist;

/**
 * This class represent saved todo item that is converted to json and sent to front-end.
 * It only contains 1 property name - which represents the unique ID of created todo item.
 */
public class SaveResponse {
	
	private String name; // variable must be called "name" - same as in JSON from  front-end

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
