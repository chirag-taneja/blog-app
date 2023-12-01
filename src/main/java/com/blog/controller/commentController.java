package com.blog.controller;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class commentController {
    CommentService commentService;

    @Autowired
    public commentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comment")
    public Comment postComment(@RequestBody CommentDto commentDto, @PathVariable long postId)
    {
        return commentService.postComment(commentDto,postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable long postId){
        List<CommentDto> allCommentsByPostId = commentService.getAllCommentsByPostId(postId);

        if (allCommentsByPostId==null || allCommentsByPostId.isEmpty())
        {
            throw new RuntimeException("No Comment Found");
        }
        return allCommentsByPostId;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/post/{postId}/comment/{commentId}")
    public CommentDto getCommentAndPostId (@PathVariable long postId,@PathVariable long commentId)
    {
        CommentDto commentAndPostId = commentService.getCommentAndPostId(postId, commentId);
        return commentAndPostId;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/post/{postId}/comment/{commentId}")
    public CommentDto updateComment(@PathVariable long postId, @PathVariable long commentId,@RequestBody CommentDto commentDto)
    {
        CommentDto commentDto1=commentService.updateComment(postId,commentId,commentDto);
        return commentDto1;
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<String> DeleteComment(@PathVariable long postId, @PathVariable long commentId)
    {
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted sucessfully",HttpStatus.OK);
    }

}
