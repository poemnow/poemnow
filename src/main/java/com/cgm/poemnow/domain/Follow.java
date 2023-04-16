package com.cgm.poemnow.domain;

import lombok.Builder;
import lombok.Data;

//@Data lombok 에서 가져오는데 getter, setter, toString 이런거 안써도
//쓰게 해줌
@Data
//builder()메서드 쓰려면 @builder 써야 함
@Builder
public class Follow {
	private int id;
	private int userId;
	private int followId;
	private String createdAt;

}
