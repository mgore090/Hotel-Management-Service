package com.MyApp.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.MyApp.entites.Hotel;
import com.MyApp.entites.Rating;
import com.MyApp.entites.User;
import com.MyApp.exceptions.ResourceNotFoundException;
import com.MyApp.repositories.UserRepositories;
import com.MyApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepositories userRepositories;

	@Autowired
//	@Qualifier("rstTempl")
//	@LoadBalanced 
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
//		if(user.getName() == null) {
//			throw new ResourceNotFoundException("You have not enter the NAME of User");
//		}else if (user.getEmail() == null) {
//			throw new ResourceNotFoundException("You have not enter the EMAIL of User");
//		}else if (user.getAbout() == null) {
//			throw new ResourceNotFoundException("You have not enter the ABOUT of User");
//		}
//		if(user.getName() == null) {
//			if (user.getEmail() == null) {
//				if (user.getAbout() == null) {
//					throw new ResourceNotFoundException("You have not enter the NAME + EMAIL + ABOUT of User");
//				}
//				throw new ResourceNotFoundException("You have not enter the NAME + EMAIL of User");
//			}
//			throw new ResourceNotFoundException("You have not enter the NAME of User");
//		}
		return userRepositories.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepositories.findAll();
	}

	@Override
	public User getUser(String userId) {

		User user = userRepositories.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with the given id not found on server ... "));

//		Rating[] ratingOfUser = restTemplate.getForObject("http://localhost:8084/ratings/user/" + user.getUserId(),
		Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/" + user.getUserId(),
				Rating[].class);
		logger.info("{} ", ratingOfUser);

		List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

		List<Rating> ratingList = ratings.stream().map(rating -> {
			ResponseEntity<Hotel> forEntity = restTemplate
					.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//			.getForEntity("http://localhost:8083/hotels/" + rating.getHotelId(), Hotel.class);
			Hotel hotel = forEntity.getBody();
			logger.info("Response Status Code : {}" + forEntity.getStatusCode());
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());

		user.setRating(ratingList);

		return user;
	}
}
