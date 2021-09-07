package com.example.CUSHProject.dto;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.enums.HairType;
import com.example.CUSHProject.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedDate;
    private int hit;
    private Rating rating;
    private HairType hairType;

    public BoardEntity toEntity() {
        return BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .hit(hit)
                .rating(rating)
                .hairType(hairType)
                .build();
    }

    @Builder
    public BoardDto(Long id, String writer, String title, String content, LocalDateTime createdDate, LocalDateTime updatedDate,int hit, Rating rating, HairType hairType) {
        this.id=id;
        this.writer=writer;
        this.title=title;
        this.content=content;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
        this.hit=hit;
        this.rating=rating;
        this.hairType=hairType;
    }
}