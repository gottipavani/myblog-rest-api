package com.myblogrestapi.service.impl;

import com.myblogrestapi.entity.Post;
import com.myblogrestapi.exception.ResourceNotFoundException;
import com.myblogrestapi.payload.PostDto;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
         Post newPost = postRepo.save(post);
         PostDto newpostDto = mapToDto(newPost);
        return newpostDto;
    }

    @Override
    public List<PostDto> getAllPost() {
         List<Post> posts = postRepo.findAll();
         return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(long id)
    {
         Post post =postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
         Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)

        );
         //now this entity obj shd  updated with update content present in postDto
         post.setTitle(postDto.getTitle());
         post.setDescription(postDto.getDescription());
         post.setContent(postDto.getContent());
//once v update the entity obj with the dto content save id tot the db
         Post updatedPost = postRepo.save(post);
         PostDto dto = mapToDto(updatedPost);
        return dto;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        postRepo.delete(post);

    }

    PostDto mapToDto(Post post) {
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;

    }


    Post mapToEntity(PostDto postDto) {
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;



    }
}
