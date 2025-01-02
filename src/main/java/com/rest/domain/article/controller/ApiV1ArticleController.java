package com.rest.domain.article.controller;

import com.rest.domain.article.entity.Article;
import com.rest.domain.article.service.ArticleService;
import com.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @Getter
    @AllArgsConstructor
    public static class ArticlesResponse{
        private final List<Article> articles;
    }

    @GetMapping("")
    public RsData<ArticlesResponse> getArticles(){
        List<Article> articles = articleService.getList();
        return RsData.of("S-1","성공",new ArticlesResponse(articles));
    }

    @Getter
    @AllArgsConstructor
    public static class ArticleResponse{
        private final Article article;
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id){
        return articleService.getArticle(id).map(article -> RsData.of(
                "S-1",
                "성공",
                new ArticleResponse(article)
        )).orElseGet(() -> RsData.of(
                "F-1",
                "%d번 게시글이 존재하지 않습니다.".formatted(id),
                null));
    }
    @Data
    public static  class WriteRequest{
        @NotBlank
        private String subject;

        @NotBlank
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class WriterResponse{
        private final Article article;
    }

    @PostMapping("")
    public RsData<WriterResponse> write(@Valid @RequestBody WriteRequest writeRequest){
        RsData<Article> writeRs = articleService.create(writeRequest.getSubject(),writeRequest.getContent());

        if (writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getResultCode(),
                writeRs.getMsg(),
                new WriterResponse(writeRs.getData()));
    }
}
