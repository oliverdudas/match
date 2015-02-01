package com.dudas.game.gameobject.board;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dudas.game.gameobject.Gem;

/**
 * Created by OLO on 24. 1. 2015
 */
public class Board {

    public static final int BOARD_WIDTH = 9;
    public static final int BOARD_HEIGHT = 9;

    public Array<Gem> gems;
    public Gem selectedGem;

    public Board() {
        init();
    }

    public void init() {
        gems = new Array<Gem>();
        gems.add(new Gem(Gem.GemType.BLUE, 0, 0));
        gems.add(new Gem(Gem.GemType.RED, 0, 1));
        gems.add(new Gem(Gem.GemType.GREEN, 0, 2));
        gems.add(new Gem(Gem.GemType.PURPLE, 0, 3));
        gems.add(new Gem(Gem.GemType.YELLOW, 0, 4));
        gems.add(new Gem(Gem.GemType.ORANGE, 0, 5));
        gems.add(new Gem(Gem.GemType.ORANGE, 0, 6));
        gems.add(new Gem(Gem.GemType.RED, 0, 7));
        gems.add(new Gem(Gem.GemType.BLUE, 0, 8));

        gems.add(new Gem(Gem.GemType.BLUE, 1, 0));
        gems.add(new Gem(Gem.GemType.RED, 1, 1));
        gems.add(new Gem(Gem.GemType.BLUE, 1, 2));
        gems.add(new Gem(Gem.GemType.PURPLE, 1, 3));
        gems.add(new Gem(Gem.GemType.ORANGE, 1, 4));
        gems.add(new Gem(Gem.GemType.BLUE, 1, 5));
        gems.add(new Gem(Gem.GemType.GREEN, 1, 6));
        gems.add(new Gem(Gem.GemType.YELLOW, 1, 7));
        gems.add(new Gem(Gem.GemType.BLUE, 1, 8));

        gems.add(new Gem(Gem.GemType.YELLOW, 2, 0));
        gems.add(new Gem(Gem.GemType.BLUE, 2, 1));
        gems.add(new Gem(Gem.GemType.RED, 2, 2));
        gems.add(new Gem(Gem.GemType.ORANGE, 2, 3));
        gems.add(new Gem(Gem.GemType.GREEN, 2, 4));
        gems.add(new Gem(Gem.GemType.BLUE, 2, 5));
        gems.add(new Gem(Gem.GemType.ORANGE, 2, 6));
        gems.add(new Gem(Gem.GemType.RED, 2, 7));
        gems.add(new Gem(Gem.GemType.PURPLE, 2, 8));

        gems.add(new Gem(Gem.GemType.BLUE, 3, 0));
        gems.add(new Gem(Gem.GemType.RED, 3, 1));
        gems.add(new Gem(Gem.GemType.GREEN, 3, 2));
        gems.add(new Gem(Gem.GemType.BLUE, 3, 3));
        gems.add(new Gem(Gem.GemType.YELLOW, 3, 4));
        gems.add(new Gem(Gem.GemType.RED, 3, 5));
        gems.add(new Gem(Gem.GemType.BLUE, 3, 6));
        gems.add(new Gem(Gem.GemType.PURPLE, 3, 7));
        gems.add(new Gem(Gem.GemType.ORANGE, 3, 8));

        gems.add(new Gem(Gem.GemType.PURPLE, 4, 0));
        gems.add(new Gem(Gem.GemType.BLUE, 4, 1));
        gems.add(new Gem(Gem.GemType.ORANGE, 4, 2));
        gems.add(new Gem(Gem.GemType.GREEN, 4, 3));
        gems.add(new Gem(Gem.GemType.RED, 4, 4));
        gems.add(new Gem(Gem.GemType.BLUE, 4, 5));
        gems.add(new Gem(Gem.GemType.YELLOW, 4, 6));
        gems.add(new Gem(Gem.GemType.ORANGE, 4, 7));
        gems.add(new Gem(Gem.GemType.BLUE, 4, 8));

        gems.add(new Gem(Gem.GemType.PURPLE, 5, 0));
        gems.add(new Gem(Gem.GemType.YELLOW, 5, 1));
        gems.add(new Gem(Gem.GemType.RED, 5, 2));
        gems.add(new Gem(Gem.GemType.GREEN, 5, 3));
        gems.add(new Gem(Gem.GemType.BLUE, 5, 4));
        gems.add(new Gem(Gem.GemType.ORANGE, 5, 5));
        gems.add(new Gem(Gem.GemType.RED, 5, 6));
        gems.add(new Gem(Gem.GemType.ORANGE, 5, 7));
        gems.add(new Gem(Gem.GemType.BLUE, 5, 8));

        gems.add(new Gem(Gem.GemType.ORANGE, 6, 0));
        gems.add(new Gem(Gem.GemType.GREEN, 6, 1));
        gems.add(new Gem(Gem.GemType.BLUE, 6, 2));
        gems.add(new Gem(Gem.GemType.PURPLE, 6, 3));
        gems.add(new Gem(Gem.GemType.RED, 6, 4));
        gems.add(new Gem(Gem.GemType.ORANGE, 6, 5));
        gems.add(new Gem(Gem.GemType.BLUE, 6, 6));
        gems.add(new Gem(Gem.GemType.YELLOW, 6, 7));
        gems.add(new Gem(Gem.GemType.YELLOW, 6, 8));

        gems.add(new Gem(Gem.GemType.RED, 7, 0));
        gems.add(new Gem(Gem.GemType.BLUE, 7, 1));
        gems.add(new Gem(Gem.GemType.PURPLE, 7, 2));
        gems.add(new Gem(Gem.GemType.ORANGE, 7, 3));
        gems.add(new Gem(Gem.GemType.BLUE, 7, 4));
        gems.add(new Gem(Gem.GemType.YELLOW, 7, 5));
        gems.add(new Gem(Gem.GemType.RED, 7, 6));
        gems.add(new Gem(Gem.GemType.ORANGE, 7, 7));
        gems.add(new Gem(Gem.GemType.GREEN, 7, 8));

        gems.add(new Gem(Gem.GemType.BLUE, 8, 0));
        gems.add(new Gem(Gem.GemType.PURPLE, 8, 1));
        gems.add(new Gem(Gem.GemType.YELLOW, 8, 2));
        gems.add(new Gem(Gem.GemType.ORANGE, 8, 3));
        gems.add(new Gem(Gem.GemType.BLUE, 8, 4));
        gems.add(new Gem(Gem.GemType.RED, 8, 5));
        gems.add(new Gem(Gem.GemType.GREEN, 8, 6));
        gems.add(new Gem(Gem.GemType.YELLOW, 8, 7));
        gems.add(new Gem(Gem.GemType.ORANGE, 8, 8));
    }

    public void swapGems(Vector2 position1, Vector2 position2) {

    }

//    public Gem findGem(float x, float y) {
//        for (gems) {
//
//        }
//    }

}
