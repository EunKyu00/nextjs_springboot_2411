package com.rest.domain.article.controller;

import com.rest.domain.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    @GetMapping("")
    public List<Article> getArticles(){
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(1L));
        articles.add(new Article(2L));
        articles.add(new Article(3L));

        return articles;
    }
    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Long id){
        Article article = new Article(id);

        return article;
    }
}
