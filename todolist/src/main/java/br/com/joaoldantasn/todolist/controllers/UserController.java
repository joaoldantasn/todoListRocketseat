package br.com.joaoldantasn.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaoldantasn.todolist.model.User;
import br.com.joaoldantasn.todolist.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping("/")
	public User create(@RequestBody User user) {
		var userCreated = this.repository.save(user);
		return userCreated;
	}
	
}
