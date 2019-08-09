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

import com.accenture.letovit.todolist.serialization.ToDoListFileDeserializer;
import com.accenture.letovit.todolist.serialization.ToDoListFileSerializer;
import com.accenture.letovit.todolist.validator.ToDoItemValidator;

/**
 * Main controller class that is handling REST request from front-end (@RestController annotation is marking
 * this class to do this)
 * @CrossOrigin annotation is just disabling some security features, that would not let our backend application
 * work with our front-end application, that is running on http:localhost:3000
 * 
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoListController {
	
	private Map<String, ToDoItem> savedToDoItems = new HashMap<>();
	
	public ToDoListController() {
		savedToDoItems = ToDoListFileDeserializer.deserialize();
	}
	
	/**
	 * POST request handling method listening on '<base_url>/todos.json' url. 
	 * This handler will create new todo item and save it to map.
	 * Unique identifier is also created and used as a key in the map
	 * Response with generated ID is returned (SaveResponse object).
	 */
	@RequestMapping(value ="todos.json", method = RequestMethod.POST)
	public SaveResponse addToDoItem(@RequestBody ToDoItem request) {
		// validate incoming request with our validator
		ToDoItemValidator.validate(request);
		
		// we will use our own current date in todo, not the one from json request
		LocalDateTime now = LocalDateTime.now();
		String prettyDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(now);
		request.setCreatedAt(prettyDateTime);
		
		// generate unique ID for each todo item
		String name = UUID.randomUUID().toString();
		// save todo item to our hash map
		savedToDoItems.put(name, request);
		
		// serialize and save map object
		serialize();
		
		// generate response object with the todo item name (which is generated id)
		SaveResponse response = new SaveResponse();
		response.setName(name);
		return response;
	}
	
	/**
	 * GET request handling method listening on '<base_url>/todos.json' URL. 
	 * Will return whole map of todo items.
	 * Map is converted from java object to json response.
	 */
	@RequestMapping(value ="todos.json", method = RequestMethod.GET)
	public Map<String, ToDoItem> fetchAllToDoItems() {
		// return whole map of todo items
		return savedToDoItems;
	}
	
	/**
	 * DELETE request handling method listening on '<base_url>/todos/{identifier}.json' URL. 
	 * This handler will just delete single todo item from the map.
	 * Method is returning nothing - that means empty response is returned to front-end.
	 * @PathVariable identifier is received in our request from front-end
	 */
	@RequestMapping(value ="/todos/{identifier}.json", method = RequestMethod.DELETE)
	public void deleteToDoItem(@PathVariable String identifier) {
		// remove single todo item from the map
		savedToDoItems.remove(identifier);
		// serialize and save map object
		serialize();
	}
	
	/**
	 * PATCH request handling method listening on '<base_url>/todos/{identifier}.json' URL. 
	 * This handler will just edit single todo item from the map with new finished flag.
	 * Method is returning nothing - that means empty response is returned to front-end.
	 * @PathVariable identifier is received in our request from front-end
	 * Inside our request body we receive the new finished flag (@RequestBody param)
	 */
	@RequestMapping(value ="/todos/{identifier}.json", method = RequestMethod.PATCH)
	public void updateToDoItem(@PathVariable String identifier, @RequestBody UpdateRequest requestBody) {
		// set single todo item to finished
		ToDoItem item = savedToDoItems.get(identifier);
		item.setFinished(requestBody.isFinished());
		// serialize and save map object
		serialize();
	}
	
	// serialize map object to file
	private void serialize() {
		ToDoListFileSerializer.serialize(savedToDoItems);
	}
}
