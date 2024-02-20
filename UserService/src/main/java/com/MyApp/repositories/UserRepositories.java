package com.MyApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MyApp.entites.User;

public interface UserRepositories  extends JpaRepository<User, String>{
	
}
