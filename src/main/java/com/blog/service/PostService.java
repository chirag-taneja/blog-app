package com.blog.service;

import com.blog.dao.CommentRepo;
import com.blog.dao.PostRepo;
import com.blog.dto.CommentDto;
import com.blog.dto.PostDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    PostRepo postRepo;

    CommentRepo commentRepo;

    ModelMapper modelMapper;
    CommentService commentService;

    @Autowired
    public PostService(PostRepo postRepo,CommentRepo commentRepo,ModelMapper modelMapper,CommentService commentService) {
        this.postRepo = postRepo;
        this.commentRepo=commentRepo;
        this.modelMapper=modelMapper;
        this.commentService =commentService;
    }

    public List<PostDto> getAllPost() {
        List<Post> post = postRepo.findAll();
        List<PostDto> postDtoList=new ArrayList<>();
        for (int i=0;i<post.size();i++)
        {
            PostDto postDto = mapPostToPostDto(post.get(i));
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    public PostDto getPostById(long postId) {

        List<CommentDto> allCommentsByPostId = commentService.getAllCommentsByPostId(postId);
        Set<CommentDto> collect = allCommentsByPostId.stream().map(i -> i).collect(Collectors.toSet());

        Post post = postRepo.findById(postId).get();
        PostDto postDto = mapPostToPostDto(post);
        return postDto;
    }

    public void deleteById(long postId) {
        postRepo.deleteById(postId);
    }

    public Post savePost(PostDto postDto) {
        Post post=Post.builder()
                .title(postDto.title())
                .content(postDto.content())
                .description(postDto.description())
                .build();

        Post savedPost = postRepo.save(post);
        return savedPost;
    }

    public Post updatePost(PostDto postDto) {
        Post psot=postRepo.findById(postDto.id()).get();

        psot.setContent(postDto.content());
        psot.setTitle(postDto.title());

        Post post1 = postRepo.save(psot);
        return post1;
    }

    public List<Post> getAllPostByPage(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        return postRepo.findAll(pageable).getContent();

    }


    private PostDto mapPostToPostDto(Post post)
    {
        List<CommentDto> allCommentsByPostId = commentService.getAllCommentsByPostId(post.getPostId());
        Set<CommentDto> collect = allCommentsByPostId.stream().map(i -> i).collect(Collectors.toSet());
        return new PostDto(post.getPostId(),post.getTitle(),post.getDescription(),post.getDescription(),collect);
    }
}
