package com.myblogrestapi.service.impl;

import com.myblogrestapi.entity.Comment;
import com.myblogrestapi.entity.Post;
import com.myblogrestapi.exception.BlogApiException;
import com.myblogrestapi.exception.ResourceNotFoundException;
import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.repository.CommentRepository;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    private PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
         Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)

        );
        Comment comment=new Comment();
         comment.setName(commentDto.getName());
         comment.setEmail(commentDto.getEmail());
         comment.setBody(commentDto.getBody());

         comment.setPost(post);
         Comment newComment = commentRepository.save(comment);

        return toDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        List<Comment> comments = commentRepository.getCommentsByPostId(postId);
        List<CommentDto> dto1 = comments.stream().map(comment -> toDto(comment)).collect(Collectors.toList());
        return dto1;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
         Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)

        );
         if(comment.getPost().getId()!=post.getId())
        {
           throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment dose not belong to this post") ;
        }

        return toDto(comment );
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
         Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
         );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", id)
        );
        if(comment.getPost().getId()!=post.getId())
         {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"post not matching with comment") ;
         }
        comment.setId(comment.getId());
         comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment newcomment = commentRepository.save(comment);

        return toDto(newcomment);
    }

    @Override
    public void deleteComment(long postId, long id) {
         Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
         );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", id)
        );
        if(comment.getPost().getId()!=post.getId())
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"post not matching with comment") ;
        }
        commentRepository.deleteById(id);

    }


    private CommentDto toDto(Comment newComment) {
        CommentDto commentDto=new CommentDto();
        commentDto.setId(newComment.getId());
        commentDto.setName(newComment.getName());
        commentDto.setEmail(newComment.getEmail());
        commentDto.setBody(newComment.getBody());
        commentDto.setPost(newComment.getPost());
        return commentDto;
    }


}
