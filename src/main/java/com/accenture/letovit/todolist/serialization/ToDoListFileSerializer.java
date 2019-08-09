package com.accenture.letovit.todolist.serialization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.accenture.letovit.todolist.ToDoItem;

public class ToDoListFileSerializer {
	
	// file name where map object is saved
	private static final String fileName = "toDoList.dat";
	
	public static void serialize(Map<String, ToDoItem> toDoItems) {
		try {
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(toDoItems);
			
			fos.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Something went wrong when saving map object.");
		}
	}

}
