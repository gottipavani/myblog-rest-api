package com.myblogrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min=2,message="post title should be at least 2 character's ")
    private String title;
    @NotEmpty
    @Size(min=10,message="post description should be at least 10 character's ")
    private String description;
    @NotEmpty
    @Size(min=10,message="post content should be at least 10 character's ")
    private String content;
}
