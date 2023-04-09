package com.cgm.poemnow.service;

import java.util.List;

import com.cgm.poemnow.domain.Poem;
public interface PoemSearchService {

    List<Poem> findPoemsByTitle(String keyword, String sortOrder);

    List<Poem> findPoemsByContent(String keyword, String sortOrder);

    List<Poem> findPoemsByTitleAndContent(String keyword, String sortOrder);

    List<Poem> findPoemsByAuthor(String keyword, String sortOrder);

    List<Poem> findPoemsByTag(String keyword, String sortOrder);


}
