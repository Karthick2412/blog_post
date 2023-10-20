package com.blog.service;

import com.blog.payload.PostDTO;
import com.blog.payload.PostResponse;

public interface PostService {
	
	PostDTO createPost(PostDTO PostDTO);
	PostResponse getAllPost(int pageNo,int pageSize);
	PostDTO getPostById(long id);
	PostDTO updatePost(PostDTO postDTO,long id);
	void deletePost(long id);

}
