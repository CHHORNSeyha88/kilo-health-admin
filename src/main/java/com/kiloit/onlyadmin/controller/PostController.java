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
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController extends BaseController {
    private final PostService postService;

    @Secured({"SCOPE_Create_Post", "ROLE_Administrator"})
    @PostMapping
    public ResponseEntity<StructureRS> createPost(@Valid @RequestBody PostCreateRequest request){
        StructureRS post = postService.createPost(request);
        return response(post);
    }

    @Secured({"SCOPE_Edit_Post", "ROLE_Administrator"})
    @PutMapping("{id}/update")
    public ResponseEntity<StructureRS> PostUpdate(@Valid @PathVariable Long id,@RequestBody PostUpdateRequest request){
        return response(postService.PostUpdate(id,request));
    }

    @Secured({"SCOPE_View_Post", "ROLE_Administrator"})
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<StructureRS> getPostList(FilterPost filterPost){
        return response(postService.getList(filterPost));
    }

    @Secured({"SCOPE_View_Post", "ROLE_Administrator"})
    @GetMapping("{id}/detail")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id){
        return response(postService.getDetail(id));
    }

    @Secured({"SCOPE_Delete_Post", "ROLE_Administrator"})
    @DeleteMapping("{id}/deletedPost")
    public ResponseEntity<StructureRS> deletedPost(@PathVariable Long id){
        return response(postService.deletePostById(id));
    }

}
