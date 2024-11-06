package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import com.kiloit.onlyadmin.base.StructureRS;
import com.kiloit.onlyadmin.constant.PermissionConstant;
import com.kiloit.onlyadmin.model.post.request.PostCreateRequest;
import com.kiloit.onlyadmin.model.post.request.PostUpdateRequest;
import com.kiloit.onlyadmin.service.PostService;
import com.kiloit.onlyadmin.util.FilterPost;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController extends BaseController {
    private final PostService postService;

    @Secured({PermissionConstant.POST.CREATE, PermissionConstant.ROLE_ADMIN})
    @PostMapping
    public ResponseEntity<StructureRS> createPost(@Valid @RequestBody PostCreateRequest request){
        return response(postService.createPost(request));
    }

    @Secured({PermissionConstant.POST.EDIT, PermissionConstant.ROLE_ADMIN})
    @PutMapping("{id}/update")
    public ResponseEntity<StructureRS> PostUpdate(@Valid @PathVariable Long id, @RequestBody PostUpdateRequest request, JwtAuthenticationToken jwt){
        return response(postService.PostUpdate(id,request,jwt));
    }

    @Secured({PermissionConstant.POST.VIEW,PermissionConstant.ROLE_ADMIN })
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<StructureRS> getPostList(FilterPost filterPost,JwtAuthenticationToken jwt){
        return response(postService.getList(filterPost,jwt));
    }

    @Secured({PermissionConstant.POST.VIEW, PermissionConstant.ROLE_ADMIN})
    @GetMapping("{id}/detail")
    public ResponseEntity<StructureRS> getDetailById(@PathVariable Long id,JwtAuthenticationToken jwt){
        return response(postService.getDetail(id,jwt));
    }

    @Secured({PermissionConstant.POST.DELETE, PermissionConstant.ROLE_ADMIN})
    @DeleteMapping("{id}/deletedPost")
    public ResponseEntity<StructureRS> deletedPost(@PathVariable Long id,JwtAuthenticationToken jwt){
        return response(postService.deletePostById(id,jwt));
    }

}
