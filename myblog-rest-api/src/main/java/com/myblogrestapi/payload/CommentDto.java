package com.myblogrestapi.payload;

import com.myblogrestapi.entity.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private long id;
    @NotEmpty(message="Is Mandatory")
    @Size(min=2,message = "name should be  at least 2 character's")
    private String name;
    @NotEmpty
    @Email(message="Invalid Email Address")
    private String email;
    @NotEmpty(message="Is Mandatory")
    @Size(min=2,message = "message should be  at least 2 character's")
    private String body;
    private Post post;

}
