package com.myblogrestapi.controller;

import com.myblogrestapi.payload.CommentDto;
import com.myblogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    private ResponseEntity<Object> createComment(@Valid @PathVariable("postId") long id, @RequestBody CommentDto commentDto, BindingResult result)
    {
        if(result.hasErrors())
        {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentDto comment = commentService.createComment(id, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable("postId") long postId) {

        List<CommentDto> commentsByPostId = commentService.getCommentsByPostId(postId);
        return commentsByPostId;
    }

    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId, @PathVariable("id") long id) {

        CommentDto dto = commentService.getCommentById(postId, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
//http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId, @PathVariable("id") long id,@RequestBody CommentDto commentDto)
    {

        CommentDto dto = commentService.updateComment(postId, id, commentDto);

              return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId")long postId,@PathVariable("id")long id)
    {
        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }
}