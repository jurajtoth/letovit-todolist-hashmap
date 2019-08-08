package com.accenture.letovit.todolist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.letovit.todolist.validator.ToDoItemValidator;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoListController {
	
	private Map<String, ToDoItem> savedToDoItems = new HashMap<>();
	
	@RequestMapping(value ="todos.json", method = RequestMethod.POST)
	public SaveResponse addToDoItem(@RequestBody ToDoItem request) {
		ToDoItemValidator.validate(request);
		
		LocalDateTime now = LocalDateTime.now();
		
		String prettyDateTime = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM).format(now);
		
		request.setCreatedAt(prettyDateTime);

		
		
		String name = UUID.randomUUID().toString();
		System.out.println("Aha co som dostal: " + request);
		savedToDoItems.put(name, request);
		SaveResponse response = new SaveResponse();
		response.setName(name);
		return response;
	}
	
	@RequestMapping(value ="todos.json", method = RequestMethod.GET)
	public Map<String, ToDoItem> fetchAllToDoItems() {
		return savedToDoItems;
	}
	
	@RequestMapping(value ="/todos/{identifier}.json", 
			method = RequestMethod.DELETE)
	public void deleteToDoItem(@PathVariable String identifier) {
		savedToDoItems.remove(identifier);
	}
	
	@RequestMapping(value ="/todos/{identifier}.json", 
			method = RequestMethod.PATCH)
	public void updateToDoItem(@PathVariable String identifier, 
			@RequestBody UpdateRequest requestBody) {
		ToDoItem item = savedToDoItems.get(identifier);
		item.setFinished(requestBody.isFinished());
	}
}
