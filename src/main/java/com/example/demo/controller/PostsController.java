package com.example.demo.controller;

import com.example.demo.service.PostsViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostsController {

    private final PostsViewService postsViewService;

    public PostsController(PostsViewService postsViewService) {
        this.postsViewService = postsViewService;
    }

    @GetMapping
    public String getPosts(Model model) {
        model.addAttribute("boardName", postsViewService.getBoardName());
        model.addAttribute("articles", postsViewService.getAllArticles());
        model.addAttribute("postsViewService", postsViewService);
        return "posts";
    }
}