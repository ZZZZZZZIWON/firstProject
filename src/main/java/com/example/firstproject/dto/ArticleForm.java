package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ArticleForm {
    private Long id;

    private String title;
    private String content;

    /*
    * DTO인 form 객체를 Entity 객체로 변환하는 역할 */
    public Article toEntity() {
        return new Article(id, title, content);
    }
}
