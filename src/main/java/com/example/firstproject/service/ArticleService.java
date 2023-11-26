package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", article.getId(), article.toString());

        //2. 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);

        //3. 잘못된 요청 처리
        if(target == null || id != article.getId()) {
            log.info("잘못된 요청 id: {}, article: {}", article.getId(), article.toString());
            return null;
        }
        //4. 업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(article);
        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target; //DB가 삭제한 대상을 Controller에 반환
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dtos를 엔티티s로 변환하기
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        //2. 엔티티 묶음을 DB에 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        //3. 강제 예외 발생
        articleRepository.findById(-1L)
                .orElseThrow(()-> new IllegalArgumentException("결제 실패"));
        //4. 결과 값 반환
        return articleList;
    }
}
