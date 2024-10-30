package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.service.PostService;
import com.kiloit.onlyadmin.util.FilterPost;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController extends BaseController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<StructureRS> createPost(@Valid @RequestBody PostCreateRequest request){
        StructureRS post = postService.createPost(request);
        return response(post);
    }

    @GetMapping("{id}/view")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id,@RequestParam Long userId){
        return response(postService.getView(id,userId));
    }

    @PutMapping("{id}/update")
    public ResponseEntity<StructureRS> PostUpdate(@Valid @PathVariable Long id,@RequestBody PostUpdateRequest request){
        return response(postService.PostUpdate(id,request));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<StructureRS> getPostList(FilterPost filterPost){
        return response(postService.getList(filterPost));
    }

    @GetMapping("{id}/detail")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id){
        return response(postService.getDetail(id));
    }

    @DeleteMapping("{id}/deletedPost")
    public ResponseEntity<StructureRS> deletedPost(@PathVariable Long id){
        return response(postService.deletePostById(id));
    }

}
