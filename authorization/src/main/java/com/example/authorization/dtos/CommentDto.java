package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CommentDto {
    private Long id;
    private String username;
    private Long staffId;
    private String text;
    private Long userId;
}
