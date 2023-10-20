package com.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;


@RestController
@RequestMapping()
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/api/v1/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
		return new ResponseEntity<>(postService.createPost(postDTO),HttpStatus.CREATED);
	}
	
	
	@GetMapping("/api/v1/posts")
	public PostResponse getAllPost(
			@RequestParam(value="pageNo", defaultValue="0", required=false) int pageNo,
			@RequestParam(value="pageSize", defaultValue="3", required=false) int pageSize
			){
		return postService.getAllPost(pageNo,pageSize);
	}
	
	@GetMapping(value = "/api/v1/posts/{id}")
	public ResponseEntity<PostDTO> getPosById(@PathVariable(name="id") long id) {
		return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable(name ="id") long id){
		PostDTO updatedPostDTO=postService.updatePost(postDTO, id);
		return new ResponseEntity<>(updatedPostDTO,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/api/v1/posts/{id}")
	public String deletePost(@PathVariable(name="id") long id) {
		postService.deletePost(id);
		return "Post deleted Successfully";
	}
}

