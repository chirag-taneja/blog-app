package com.blog.controller;

import com.blog.dto.PostDto;
import com.blog.entity.Post;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")

public class PostController {

    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> getAllPost()
    {
        List<Post> allPost = postService.getAllPost();

        if (allPost.isEmpty()|| allPost==null)
        {
            throw new RuntimeException("Post Not Found");
        }
        return allPost;
    }
    @GetMapping("/posts/query")
    public List<Post> getAllPostByPage(@RequestParam(name = "no",required = false,defaultValue = "0")int pageNo,
                                       @RequestParam(name = "size",required = false,defaultValue = "2")int pageSize,
                                       @RequestParam(name="sort",required=false,defaultValue="title") String sortBy,
                                       @RequestParam(name="dir",required=false,defaultValue="asc") String sortDir
    )
    {
        List<Post> allPost = postService.getAllPostByPage(pageNo,pageSize,sortBy,sortDir);

        if (allPost.isEmpty()|| allPost==null)
        {
            throw new RuntimeException("Post Not Found");
        }
        return allPost;
    }
    @GetMapping("/post/{postId}")
    public Post getPostById(@PathVariable long postId)
    {
        Post postById = postService.getPostById(postId);


        if (postById==null)
        {
            throw new RuntimeException("Post Not Found");
        }
        return  postById;
    }

    @DeleteMapping("/post/{postId}")
    public String deleteById(@PathVariable long postId)
    {
        postService.deleteById(postId);

        return "Delete Successfully";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public Post savePost(@RequestBody PostDto postDto)
    {
        Post post = postService.savePost(postDto);
        if (post==null)
        {
            throw new RuntimeException("Post Not saved");
        }

        return post;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/post")
    public Post updatePost(@RequestBody PostDto postDto)
    {
        Post post = postService.updatePost(postDto);
        if (post==null)
        {
            throw new RuntimeException("Post Not saved");
        }

        return post;
    }


}
