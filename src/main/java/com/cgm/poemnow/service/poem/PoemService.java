package com.cgm.poemnow.service.poem;

import java.util.List;

import com.cgm.poemnow.domain.Poem;

public interface PoemService {

	List<Poem> listPoem();

	Poem addPoem(Poem poemRequest);

}
