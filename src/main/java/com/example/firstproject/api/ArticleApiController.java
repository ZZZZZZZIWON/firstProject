package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Slf4j
public class ArticleApiController {

    @Autowired
    private ArticleService articleService;

    //GET
    @GetMapping()
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    //POST
    @PostMapping()
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
       Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
      Article deleted = articleService.delete(id);
      return (deleted != null) ?
              ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
              ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
