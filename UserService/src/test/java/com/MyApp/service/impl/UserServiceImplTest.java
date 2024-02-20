package com.MyApp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.MyApp.entites.User;
import com.MyApp.repositories.UserRepositories;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userServiceImpl;
	@Mock
	UserRepositories userRepositories;

	@Test
	void saveUserTest() {
		User input = new User();
		User output = new User();
		output.setUserId("23456");
		output.setName("Kar");
		output.setEmail("k@gmail.com");
		output.setAbout("Hotel is good");
		when(userRepositories.save(Mockito.any())).thenReturn(output);
		User user = userServiceImpl.saveUser(input);
		assertNotNull(user);
		assertEquals("23456", user.getUserId());
		assertEquals("Kar", user.getName());
		assertEquals("k@gmail.com", user.getEmail());
		assertEquals("Hotel is good", user.getAbout());
	}
}
