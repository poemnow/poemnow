package com.cgm.poemnow.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {

    private int userId;
    private int poemId;
    private String createAt;

}