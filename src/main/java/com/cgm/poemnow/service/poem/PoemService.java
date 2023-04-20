package com.cgm.poemnow.service.poem;

import com.cgm.poemnow.domain.Poem;

import java.util.List;

public interface PoemService {

    int addPoem(Poem poem);

    List<Poem> findAllPoems();

    Poem findPoemById(int id);

    int modifyPoem(Poem poem);

    int removePoem(int id);
}
