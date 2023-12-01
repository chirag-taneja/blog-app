package com.blog.service;

import com.blog.dao.CommentRepo;
import com.blog.dao.PostRepo;
import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    CommentRepo commentRepo;
    PostRepo postRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo, PostRepo postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    public Comment postComment(CommentDto commentDto, long postId) {
        Post post = postRepo.findById(postId).get();
        Comment comment=new Comment(commentDto.comment(), commentDto.name());
        comment.setPost(post);
        return commentRepo.save(comment);
    }

    public List<CommentDto> getAllCommentsByPostId(long postId) {
        List<Comment> byPostPostId = commentRepo.findByPostPostId(postId);

        List<CommentDto> list=new ArrayList<>();
        for (int i=0;i<byPostPostId.size();i++)
        {
            Comment comment=byPostPostId.get(i);
            CommentDto commentDto=new CommentDto(comment.getId(),comment.getComment(),comment.getName());
            list.add(commentDto);
        }
        return list;
    }

    public CommentDto getCommentAndPostId(long postId, long commentId) {
        Post post = postRepo.findById(postId).get();
        Comment comment=commentRepo.findById(commentId).get();

        System.out.println(comment.getPost().getPostId());
        if (!(comment.getPost().getPostId()==postId))
        {
            throw new RuntimeException("this comment is not related to this post");
        }
        CommentDto commentDto=new CommentDto(comment.getId(),comment.getComment(),comment.getName());
        return commentDto;
    }

    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        Post post=postRepo.findById(postId).get();

        Comment comment = commentRepo.findById(commentId).get();
        if (!(comment.getPost().getPostId()==postId))
        {
            throw new RuntimeException("this comment is not related to this post");
        }
        comment.setComment(commentDto.comment());
        comment.setName(commentDto.name());

        Comment save = commentRepo.save(comment);

        CommentDto commentDto1=new CommentDto(save.getId(),save.getComment(),save.getName());
        return commentDto1;

    }

    public void deleteComment(long postId, long commentId) {
        Post post=postRepo.findById(postId).get();

        Comment comment = commentRepo.findById(commentId).get();
        if (!(comment.getPost().getPostId()==postId))
        {
            throw new RuntimeException("this comment is not related to this post");
        }
        commentRepo.deleteById(commentId);
    }
}
