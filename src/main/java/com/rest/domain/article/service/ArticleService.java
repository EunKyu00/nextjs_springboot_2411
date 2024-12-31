package com.rest.domain.article.service;


import com.rest.domain.article.entity.Article;
import com.rest.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList(){
        return articleRepository.findAll();
    }

    public Optional<Article> getArticle(Long id){
        return articleRepository.findById(id);
    }

    public void create(String subject, String content) {
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .build();
        articleRepository.save(article);
    }
}
