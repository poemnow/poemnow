package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Poem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PoemMapper {

	int insertPoem(Poem poem);

	List<Poem> selectAllPoems();

	Poem selectPoemById(int id);

	int updatePoem(Poem poem);

	int deletePoem(int id);

}