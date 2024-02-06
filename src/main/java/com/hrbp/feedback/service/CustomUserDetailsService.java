package com.hrbp.feedback.service;

import org.springframework.stereotype.Service;

import com.hrbp.feedback.repository.UserRepository;
import com.hrbp.feedback.model.entity.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
	    User user = userRepository.findByUserId(Integer.parseInt(userId));
	    List<String> roles = new ArrayList<>();
	    roles.add("USER");
	    return org.springframework.security.core.userdetails.User.builder()
	            .username(user.getUserId().toString())
	            .password(user.getPasword())
	            .roles(roles.toArray(new String[0]))
	            .build();
	}

}