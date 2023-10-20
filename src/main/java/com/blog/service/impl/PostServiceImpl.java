package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.exception.ResourceNotFoundException;
import com.blog.models.Post;
import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;


@Service
public class PostServiceImpl implements PostService{
	
	private PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}



	@Override
	public PostDTO createPost(PostDTO PostDTO) {
		Post post=mapToEntity(PostDTO);
		Post newPost=postRepository.save(post);
		return mapToDTO(newPost);
	}
	
	public Post mapToEntity(PostDTO postDTO) {
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		return post;
	}
	
	public PostDTO mapToDTO(Post post) {
		PostDTO postDTO=new PostDTO();
		postDTO.setTitle(post.getTitle());
		postDTO.setDescription(post.getDescription());
		postDTO.setContent(post.getContent());
		return postDTO;
	}



	@Override
	public PostResponse getAllPost(int pageNo,int pageSize) {
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		
		Page<Post> posts=postRepository.findAll(pageable);
		
		List<Post> listOfPosts=posts.getContent();
		List<PostDTO> content= listOfPosts.stream().map(post ->mapToDTO(post)).collect(Collectors.toList());
		PostResponse postResponse= new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		return postResponse;
		
	}



	@Override
	public PostDTO getPostById(long id) {
		Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		return mapToDTO(post);
	}



	@Override
	public PostDTO updatePost(PostDTO postDTO,long id) {
		Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		Post updatedPost=postRepository.save(post);
		return mapToDTO(updatedPost);
	}



	@Override
	public void deletePost(long id) {
		Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));
		postRepository.delete(post);
	}
	
}
