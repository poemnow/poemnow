package com.cgm.poemnow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentLike {

    private int userId;
    private int commentId;
    private String createAt;

}