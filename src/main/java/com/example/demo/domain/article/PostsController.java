package com.example.demo.domain.article;

import com.example.demo.domain.board.Board;
import com.example.demo.domain.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostsController {

    private final BoardService boardService;
    private final ArticleService articleService;

    public PostsController(BoardService boardService, ArticleService articleService) {
        this.boardService = boardService;
        this.articleService = articleService;
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam Long boardId, Model model) {
        Board board = boardService.getById(boardId);
        List<Article> articles = articleService.getByBoardId(boardId);

        model.addAttribute("boardName", board.getName());
        model.addAttribute("articles", articles);

        return "posts";
    }
}
