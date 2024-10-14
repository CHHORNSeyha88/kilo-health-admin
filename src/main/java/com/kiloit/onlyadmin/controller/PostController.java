package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.service.PostService;
import com.kiloit.onlyadmin.util.FilterPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController extends BaseController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<StructureRS> createPost(@RequestBody PostCreateRequest request){
        StructureRS post = postService.createPost(request);
        return response(post);
    }

    @GetMapping("{id}/getDetail")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id,@RequestParam Long userId){
        return response(postService.getPostDetail(id,userId));
    }

    @PutMapping("{id}/updatePost")
    public ResponseEntity<StructureRS> PostUpdate(@PathVariable Long id,@RequestBody PostUpdateRequest request){
        return response(postService.PostUpdate(id,request));
    }
    @GetMapping
    public ResponseEntity<StructureRS> getPostList(FilterPost filterPost){
        return response(postService.getList(filterPost));
    }

    @DeleteMapping("{id}/deletedPost")
    public ResponseEntity<StructureRS> deletedPost(@PathVariable Long id){
        return response(postService.deletePostById(id));
    }

}
