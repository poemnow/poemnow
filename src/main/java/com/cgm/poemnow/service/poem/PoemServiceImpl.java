package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.mapper.PoemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PoemServiceImpl implements PoemService {

	@Autowired
	private PoemMapper poemMapper;

	@Override
	public int addPoem(Poem poem) {
		return poemMapper.insertPoem(poem);
	}

	@Override
	public List<Poem> findAllPoems() {
		return poemMapper.selectAllPoems();
	}

	@Override
	public Poem findPoemById(int id) {
		return poemMapper.selectPoemById(id);
	}

	@Override
	public int modifyPoem(Poem poem) {
		return poemMapper.updatePoem(poem);
	}

	@Override
	public int removePoem(int id) {
		return poemMapper.deletePoem(id);
	}

}