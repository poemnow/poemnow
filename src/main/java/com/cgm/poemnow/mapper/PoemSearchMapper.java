package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Poem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface  PoemSearchMapper {

    List<Poem> selectPoemsByTitle(String keyword, String sortOrder);

    List<Poem> selectPoemsByContent(String keyword, String sortOrder);

    List<Poem> selectPoemsByTitleAndContent(String keyword, String sortOrder);

    List<Poem> selectPoemsByAuthor(String keyword, String sortOrder);

    List<Poem> selectPoemsByTag(String keyword, String sortOrder);

}
