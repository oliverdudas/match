package com.dudas.game.gameobject.board;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by OLO on 1. 2. 2015
 */
public class BoardTest {

    public static final int EXPECTED_CELL_COUNT = 81; // TODO: maybe this hould be loaded from config

    @Test
    public void initTest() {
        Board board = new Board();
        assertEquals(board.gems.size, EXPECTED_CELL_COUNT);
    }

    @Test
    public void testBoardCellCount() throws Exception {
        assertEquals(getBoardCellCount(), EXPECTED_CELL_COUNT);
    }

    private int getBoardCellCount() {
        return Board.BOARD_HEIGHT * Board.BOARD_HEIGHT;
    }

    @Test
    @Ignore
    public void thisIsIgnored() {
    }
}
