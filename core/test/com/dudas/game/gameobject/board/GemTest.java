package com.dudas.game.gameobject.board;

import com.dudas.game.gameobject.Gem;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by OLO on 1. 2. 2015
 */
public class GemTest {

    @Test
    public void testGemCreation() throws Exception {
        assertNotNull(Gem.createNewRandomGem());
    }
}
