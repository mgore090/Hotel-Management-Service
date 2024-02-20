package com.MyApp.controller;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyApp.entites.User;
import com.MyApp.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	User user5 = new User();
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}

	@GetMapping("/{userId}")
	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		User user1 = userService.getUser(userId);
		return ResponseEntity.ok(user1);
	}

	// creating fall back method
	public ResponseEntity<User> ratingHotelFallBack(String userId,Exception ex){
		logger.info("Fallback is executed because service is down :",ex.getMessage());
//		User user = User.builder()
//			.email("dummy@gmail.com")
//			.name("Dummy")
//			.about("This user is created dummy bcz some service are down .. !")
//			.userId("7058805659")
//			.build();
//		String s = "User is created for user ";
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
}
