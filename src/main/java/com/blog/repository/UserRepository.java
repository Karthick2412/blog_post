package com.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.User;



public interface UserRepository extends JpaRepository<User,Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameOrEmail(String userName,String emial);
	Optional<User> findByUsername(String userName);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}

