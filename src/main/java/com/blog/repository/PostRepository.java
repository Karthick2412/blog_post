package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Post;



public interface PostRepository extends JpaRepository<Post,Long>{

}