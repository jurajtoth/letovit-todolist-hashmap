package com.accenture.letovit.todolist.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import com.accenture.letovit.todolist.ToDoItem;

public class ToDoListFileDeserializer {
	
	// file name from which map object is read
	private static final String fileName = "toDoList.dat";
	
	public static Map<String, ToDoItem> deserialize() {
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Object savedObject = ois.readObject();
			Map<String, ToDoItem> savedMap = (Map<String, ToDoItem>) savedObject;
			
			fis.close();
			ois.close();
			
			return savedMap;
		} catch (Exception e) {
			// return empty map if something went wrong or file did not exist
			return new HashMap<String, ToDoItem>();
		}
	}

}
