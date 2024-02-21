package com.hrbp.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrbp.feedback.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUserId(Integer userId);

}
