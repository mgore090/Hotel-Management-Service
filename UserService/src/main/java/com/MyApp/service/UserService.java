package com.MyApp.service;

import java.util.List;

import com.MyApp.entites.User;

public interface UserService {
	User saveUser(User user);
	
	List<User> getAllUser();
	
	User getUser(String userId);
	
	//Delete 
	//Update
}
