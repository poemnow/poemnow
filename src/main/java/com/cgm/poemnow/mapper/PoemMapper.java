package com.cgm.poemnow.mapper;

import java.util.List;

import com.cgm.poemnow.domain.Poem;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PoemMapper {

	public List<Poem> poemList();

}
