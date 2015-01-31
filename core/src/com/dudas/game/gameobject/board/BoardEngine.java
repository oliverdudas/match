package com.dudas.game.gameobject.board;

/**
 * Created by OLO on 28. 1. 2015
 */
public interface BoardEngine {

    void init();

    boolean swapGems(int gem1Index, int gem2index);
}
