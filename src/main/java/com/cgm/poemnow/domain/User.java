package com.cgm.poemnow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String user_id;
    private String user_nickname;
    private String user_name;
    private Timestamp user_birth;
    private String email;
    private String user_phone;
    private String profile_pic;
    private String bio;
    private String social_url;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int follower_cnt;
    private int following_cnt;
    private short active;


}
