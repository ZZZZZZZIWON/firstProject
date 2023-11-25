package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {

        log.info(form.toString());
        //1. DTO를 Entity로 변환
        Article article = form.toEntity();

        //2. Repository로 Entity를 DB에 저장
        Article saved = articleRepository.save(article);
        /*
        * 저장한 Article인 saved에는 id값이 있음
        * Therefore, saved.getId()를 호출 -> saved 객체의 id 값 파악 가능
        * */
        return "redirect:/articles/" + saved.getId() ;
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        //1. DB에서 모든 Article 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        //2. 가져온 Article 묶음을 모델에 등록
        model.addAttribute("articleList", articleEntityList);
        //3. 사용자에게 보여줄 뷰 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        Article articleEntity = form.toEntity();
        //2-1 DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2 기존 데이터 값 갱신하기
        if(target != null) {
            articleRepository.save(articleEntity);
        }
        /*
        * 비정상적인 수정 요청을 하는 경우)
        * 수정 시 입력 대상의 존재 여부를 검증해야 함
        * */

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        //2. 대상 엔티티 삭제하기
        if(target!= null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다.");
        }
        //3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}

