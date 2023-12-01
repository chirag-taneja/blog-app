package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;


public record PostDto(long id, String title, String description, String content, Set<CommentDto> commentDtoSet) {
}


