package com.dudas.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dudas.game.gameobject.Gem;
import com.dudas.game.gameobject.board.Board;

/**
 * Created by OLO on 30. 1. 2015
 */
public class BoardRenderer {

    private TextureAtlas assetsAtlas;
    private TextureAtlas.AtlasRegion boardAtlasRegion;
    public TextureAtlas.AtlasRegion red;
    public TextureAtlas.AtlasRegion green;
    public TextureAtlas.AtlasRegion blue;
    public TextureAtlas.AtlasRegion purple;
    public TextureAtlas.AtlasRegion yellow;
    public TextureAtlas.AtlasRegion orange;

    public BoardRenderer() {
        init();
    }

    private void init() {
        assetsAtlas = new TextureAtlas("images/match.pack.atlas");
        boardAtlasRegion = assetsAtlas.findRegion("board");
        red = assetsAtlas.findRegion("red_square");
        green = assetsAtlas.findRegion("green_square");
        blue = assetsAtlas.findRegion("blue_square");
        purple = assetsAtlas.findRegion("purple_square");
        yellow = assetsAtlas.findRegion("yellow_square");
        orange = assetsAtlas.findRegion("orange_square");
    }

    public void renderBoard(SpriteBatch batch, Board board) {
        batch.draw(boardAtlasRegion, 0, 0, Board.BOARD_WIDTH, Board.BOARD_HEIGHT);
        renderGems(batch, board);
    }

    private void renderGems(SpriteBatch batch, Board board) {
        Gem[] gemArray = board.gems.toArray(Gem.class);
        for (Gem gem : gemArray) {
            if (!gem.getType().equals(Gem.GemType.EMPTY)) {
                renderGem(batch, gem);
            }
        }
    }

    private void renderGem(SpriteBatch batch, Gem gem) {
        batch.draw(
                getRegionByGemType(gem.getType()),
                gem.position.x,
                gem.position.y,
                gem.origin.x,
                gem.origin.y,
                gem.dimension.x,
                gem.dimension.y,
                gem.scale.x,
                gem.scale.x,
                gem.rotation
        );
    }

    private TextureAtlas.AtlasRegion getRegionByGemType(Gem.GemType type) {
        switch (type) {
            case RED:
                return red;
            case GREEN:
                return green;
            case BLUE:
                return blue;
            case PURPLE:
                return purple;
            case YELLOW:
                return yellow;
            case ORANGE:
                return orange;
        }
        throw new RuntimeException("There is no region for gemType: " + type.name());
    }

    public void dispose() {
        assetsAtlas.dispose();
    }
}
