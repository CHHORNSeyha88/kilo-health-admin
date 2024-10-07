package com.kiloit.onlyadmin.controller;

import com.kiloit.onlyadmin.base.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController extends BaseController {

}
