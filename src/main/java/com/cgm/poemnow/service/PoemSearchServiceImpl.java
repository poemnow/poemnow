package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.mapper.PoemSearchMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoemSearchServiceImpl implements PoemSearchService {

    @Autowired
    private PoemSearchMapper poemSearchMapper;

    @Override
    public List<Poem> findPoemsByTitle(String keyword, String sortOrder) {
        return poemSearchMapper.selectPoemsByTitle(keyword, sortOrder);
    }

    @Override
    public List<Poem> findPoemsByContent(String keyword, String sortOrder) {
        return poemSearchMapper.selectPoemsByContent(keyword, sortOrder);
    }

    @Override
    public List<Poem> findPoemsByTitleAndContent(String keyword, String sortOrder) {
        return poemSearchMapper.selectPoemsByTitleAndContent(keyword, sortOrder);
    }

    @Override
    public List<Poem> findPoemsByAuthor(String keyword, String sortOrder) {
        return poemSearchMapper.selectPoemsByAuthor(keyword, sortOrder);
    }

    @Override
    public List<Poem> findPoemsByTag(String keyword, String sortOrder) {
        return poemSearchMapper.selectPoemsByTag(keyword, sortOrder);
    }
}
