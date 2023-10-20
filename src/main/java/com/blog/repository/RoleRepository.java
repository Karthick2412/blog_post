package com.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Role;



public interface RoleRepository extends JpaRepository<Role,Long>{
	Optional<Role> findByName(String name);

}