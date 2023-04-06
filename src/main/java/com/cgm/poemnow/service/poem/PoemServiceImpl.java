package com.cgm.poemnow.service.poem;

import java.util.List;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.mapper.PoemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PoemServiceImpl implements PoemService {

	@Autowired
	private PoemMapper poemMapper;

	@Override
	public List<Poem> listPoem() {
		return poemMapper.poemList();
	}

	@Override
	public Poem addPoem(Poem poem) {
		return poemMapper.insertPoem(poem);
	}

}
