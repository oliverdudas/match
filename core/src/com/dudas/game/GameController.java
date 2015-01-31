package com.dudas.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dudas.game.gameobject.Gem;
import com.dudas.game.gameobject.SwapPair;
import com.dudas.game.gameobject.board.Board;

import java.util.Iterator;

/**
 * Created by OLO on 24. 1. 2015
 */
public class GameController extends InputAdapter {

    public static final String TAG = GameController.class.getName();

    public static final int BOARD_MIN_POSITION = 0;
    public static final int BOARD_MAX_POSITION = 8;
    private static final Vector2 DIRECTION_RIGHT = new Vector2(1.0f, 0.0f);
    private static final Vector2 DIRECTION_LEFT = new Vector2(-1.0f, 0.0f);
    private static final Vector2 DIRECTION_TOP = new Vector2(0.0f, 1.0f);
    private static final Vector2 DIRECTION_BOTTOM = new Vector2(0.0f, -1.0f);

    public Board board;
    public CameraHelper cameraHelper;
    private Vector3 touchPosition;
    private Vector2 swapDirectionVector;
    private Vector2 summaryDirectionVector;
    private Array<SwapPair> swapPairArray;
    private Array<Gem> gemsToAnimateClearing;
    private Array<Gem> gemsToAdd;
    private Array<Gem> gemsToClearTemp;
    private Array<Gem> fallingGems;
    private boolean swapEnabled;

    public GameController() {
        init();
    }

    private void init() {
        Gdx.app.debug(TAG, "Initializing Game.");

        board = new Board();
        cameraHelper = new CameraHelper();
        touchPosition = new Vector3();
        swapPairArray = new Array<SwapPair>();
        gemsToAnimateClearing = new Array<Gem>();
        gemsToClearTemp = new Array<Gem>();
        swapEnabled = true;
        gemsToAdd = new Array<Gem>();
        summaryDirectionVector = new Vector2();
        swapDirectionVector = new Vector2();
        fallingGems = new Array<Gem>();
        Gdx.input.setInputProcessor(this);
    }

    private void reset() {
        Gdx.app.debug(TAG, "Reseting Game.");
        init();
    }

    public void update(float deltaTime) {
        handleAdding(deltaTime);
        handleInput(deltaTime);

        animateSwapPair(deltaTime);
        animateClearing(deltaTime);
        animateFallingGames(deltaTime);
    }

    private void handleAdding(float deltaTime) {
        if (gemsToAdd.size > 0) {
            for (Gem gem : gemsToAdd) {
                Gem lastGemInColumn = handleGemsFallingInColumn(gem);
                gem.position.set(lastGemInColumn.position.x, lastGemInColumn.position.y + DIRECTION_TOP.y);
                gem.setFallTo(gem.position.x, gem.position.y + DIRECTION_BOTTOM.y);
            }
//        fallingGems.addAll(gemsToAdd);
            gemsToAdd.clear();
        }
    }

    private Gem handleGemsFallingInColumn(Gem gem) {
        fallingGems.add(gem);
        board.changeGemTypeRandomly(gem);
        int nextAboveGemindex = getNextAboveGemindex(gem);
        if (nextAboveGemindex <= 80) {
            Gem aboveGem = getGem(nextAboveGemindex);
            if (aboveGem != null && aboveGem.position.x == gem.position.x) {
                aboveGem.setFallTo(aboveGem.position.x, aboveGem.position.y + DIRECTION_BOTTOM.y);
                return handleGemsFallingInColumn(aboveGem);
            }
        }
        return gem;
    }

    private int getNextAboveGemindex(Gem gem) {
        return gem.getIndex() + 1;
    }

    private void handleClearing(Gem gem) {
        if (gem.isReady() && !gem.getType().equals(Gem.GemType.EMPTY)) {
            gemsToClearTemp.clear();

            gatherGemsToClear(gem, DIRECTION_LEFT);
            gatherGemsToClear(gem, DIRECTION_RIGHT);
            if (gemsToClearTemp.size < 2) {
                gemsToClearTemp.clear();
            }
            gatherGemsToClear(gem, DIRECTION_TOP);
            gatherGemsToClear(gem, DIRECTION_BOTTOM);
            if (gemsToClearTemp.size < 2) {
                gemsToClearTemp.clear();
            }

            if (gemsToClearTemp.size >= 2) {
                gemsToClearTemp.add(gem);
            }

//            if (gemsToClearTemp.size > 0) {
//                Gdx.app.debug(TAG, "Gems(" + gemsToClearTemp.get(0).getType().name() + ") to clear size: " + gemsToClearTemp.size);
//            }
//            clearGems();
            if (gemsToClearTemp.size > 2) {
                blockGems(gemsToClearTemp);
                gemsToAnimateClearing.addAll(gemsToClearTemp);
                gemsToClearTemp.clear();
            }
        }
    }

    private void blockGems(Array<Gem> gemsToBlock) {
        for (Gem gem : gemsToBlock) {
            gem.block();
        }
    }

    private void gatherGemsToClear(Gem gem, Vector2 direction) {
        populateGemsToClear(gem, false, direction);
    }

//    private void clearGems() {
//        if (gemsToAnimateClearing.size >= 3) {
//            for (Gem gemToClear : gemsToAnimateClearing) {
//                Gdx.app.debug(TAG, "Clearing " + gemToClear.getType().name() + "(" + gemToClear.position.x + ", " + gemToClear.position.y + ")");
//                gemToClear.setType(Gem.GemType.EMPTY);
//            }
//        }
//    }

    private void populateGemsToClear(Gem gem, boolean include, Vector2 directionVector) {
        if (gem.isReady() && !gem.getType().equals(Gem.GemType.EMPTY)) {
            if (include) {
                gemsToClearTemp.add(gem);
            }
            summaryDirectionVector.set(0, 0); // reset
            summaryDirectionVector.add(gem.position).add(directionVector);
            if (summaryDirectionVector.x >= 0 && summaryDirectionVector.y >= 0 && summaryDirectionVector.x <= 8 && summaryDirectionVector.y <= 8) {
                float nextGemIndex = vectorCoordinatesToIndex(summaryDirectionVector.x, summaryDirectionVector.y);
                Gem nextGemToCheck = getGem((int) nextGemIndex);
                if (nextGemToCheck != null && gem.getType().equals(nextGemToCheck.getType())) {
                    populateGemsToClear(nextGemToCheck, true, directionVector);
                }
            }
        }
    }

    private void animateFallingGames(float deltaTime) {
        float moveSpeed = 5.0f * deltaTime;
        for (Iterator<Gem> iterator = fallingGems.iterator(); iterator.hasNext();) {
            Gem fallingGem = iterator.next();
            if (fallingGem.isFalling()) {
                fallingGem.updateFalling(moveSpeed);
                boolean fallingEnded = !fallingGem.isFalling();
                if (fallingEnded) {
                    fallingGem.setReady();

                    iterator.remove();
                }
            }
        }
    }

    private void animateClearing(float deltaTime) {
        float moveSpeed = 5.0f * deltaTime;

        for (Iterator<Gem> iterator = gemsToAnimateClearing.iterator(); iterator.hasNext();) {
            Gem gemToClear = iterator.next();
            if (gemToClear.isBlocked()) {
//            Gdx.app.debug(TAG, "Animating gem(" + gemToClear.getType().name() + ") to clear");
                gemToClear.scale.x -= moveSpeed;
                gemToClear.scale.y -= moveSpeed;
                gemToClear.rotation += 5;
                if (gemToClear.scale.x < 0.1) {
                    gemToClear.setType(Gem.GemType.EMPTY);
                    gemToClear.scale.set(1, 1);
                    gemsToAdd.add(gemToClear);
                    iterator.remove();

                }
            } else {
                throw new RuntimeException("Not blocked gem can not be animated during clearing!");
            }
        }
    }

    private void animateSwapPair(float deltaTime) {
        float moveSpeed = 5.0f * deltaTime;
        for (SwapPair swapPair : swapPairArray) {
            Gem gem1 = swapPair.gem1;
            Gem gem2 = swapPair.gem2;
            // horizontal move
            if (swapPair.moveDirectionX != 0) {
                gem1.position.x += moveSpeed;
                gem2.position.x -= moveSpeed;

                if (gem1.position.x > swapPair.rightPositionXOrigin) {
                    gem1.position.x = swapPair.rightPositionXOrigin;
                    gem2.position.x = swapPair.leftPositionXOrigin;

                    gem1.setReady();
                    gem2.setReady();
                    this.swapPairArray.removeValue(swapPair, true);
                    this.swapPairArray.shrink();


                    // synchronize order in board.gems Array with real gems positions
                    board.gems.swap(swapPair.gem1IndexOrigin, swapPair.gem2IndexOrigin);

                    handleClearing(gem1);
                    handleClearing(gem2);
                }
                // vertical move
            } else if (swapPair.moveDirectionY != 0) {
                gem1.position.y += moveSpeed;
                gem2.position.y -= moveSpeed;

                if (gem1.position.y > swapPair.rightPositionYOrigin) {
                    gem1.position.y = swapPair.rightPositionYOrigin;
                    gem2.position.y = swapPair.leftPositionYOrigin;

                    gem1.setReady();
                    gem2.setReady();
                    this.swapPairArray.removeValue(swapPair, true);
                    this.swapPairArray.shrink();

                    // synchronize order in board.gems Array with real gems positions
                    board.gems.swap(swapPair.gem1IndexOrigin, swapPair.gem2IndexOrigin);

                    handleClearing(gem1);
                    handleClearing(gem2);
                }
            } else {
                throw new RuntimeException("Unsupported move.");
            }
        }
    }

    private void handleInput(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            Vector2 selectedGemPosition = board.selectedGem.position;
            if (selectedGemPosition.x > BOARD_MIN_POSITION) {
                selectedGemPosition.x = selectedGemPosition.x - 1;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            Vector2 selectedGemPosition = board.selectedGem.position;
            if (selectedGemPosition.x < BOARD_MAX_POSITION) {
                selectedGemPosition.x = selectedGemPosition.x + 1;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            Vector2 selectedGemPosition = board.selectedGem.position;
            if (selectedGemPosition.y < BOARD_MAX_POSITION) {
                selectedGemPosition.y = selectedGemPosition.y + 1;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            Vector2 selectedGemPosition = board.selectedGem.position;
            if (selectedGemPosition.y > BOARD_MIN_POSITION) {
                selectedGemPosition.y = selectedGemPosition.y - 1;
            }
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        if (isSwapEnabled()) {

            Gem selectedGem = board.selectedGem;

            touchPosition.set(screenX, screenY, 0); //when the screen is touched, the coordinates are inserted into the vector
            cameraHelper.getCamera().unproject(touchPosition); // calibrates the input to your camera's dimentions
            float touchPositionX = MathUtils.floor(touchPosition.x);
            float touchPositionY = MathUtils.floor(touchPosition.y);

            if (selectedGem != null && selectedGem.isReady()) {

                int moveDirectionX = (int) (touchPositionX - selectedGem.position.x);
                int moveDirectionY = (int) (touchPositionY - selectedGem.position.y);
                if (isHorizontalMove(moveDirectionX, moveDirectionY)) {
                    clearSelection("SWAP");
                    swapGemsHorizontaly(selectedGem, moveDirectionX);
                } else if (isVerticalMove(moveDirectionX, moveDirectionY)) {
                    clearSelection("SWAP");
                    swapGemsVerticaly(selectedGem, moveDirectionY);
                }
                return true;
            } else {
                int gemIndex = (int) vectorCoordinatesToIndex(touchPositionX, touchPositionY);
                selectGem(gemIndex);
            }

            return false;
        }
        return false;
    }



    private float vectorCoordinatesToIndex(float touchPositionX, float touchPositionY) {
        return touchPositionX * 9 + touchPositionY;
    }

    private void swapGemsHorizontaly(Gem selectedGem, int moveDirectionX) {
        int selectedGemIndex = selectedGem.getIndex();
        int gemToSwapIndex = calculateSwapIndex((moveDirectionX * 9), selectedGemIndex);
        Gem gemToSwap = getGem(gemToSwapIndex);
        if (gemToSwap.isReady()) {
            if (moveDirectionX == 1) {
                disableSwap();
                selectedGem.block();
                gemToSwap.block();
                Gdx.app.debug(TAG, "Swaping " + selectedGem.getType().name() + "(" + selectedGem.position.x + ", " + selectedGem.position.y + ") with " + gemToSwap.getType().name() + "(" + gemToSwap.position.x + ", " + gemToSwap.position.y + ")");
                swapPairArray.add(new SwapPair(selectedGem, gemToSwap, moveDirectionX, 0)); // TODO: prevent new instance, maybe a stack of 5 preinstantioned SwapPair could be created in init
            } else if (moveDirectionX == -1) {
                disableSwap();
                selectedGem.block();
                gemToSwap.block();
                Gdx.app.debug(TAG, "Swaping " + selectedGem.getType().name() + "(" + selectedGem.position.x + ", " + selectedGem.position.y + ") with " + gemToSwap.getType().name() + "(" + gemToSwap.position.x + ", " + gemToSwap.position.y + ")");
                swapPairArray.add(new SwapPair(gemToSwap, selectedGem, moveDirectionX, 0)); // TODO: prevent new instance, maybe a stack of 5 preinstantioned SwapPair could be created in init
            } else {
                throw new RuntimeException("Can not be swapped with moveDirectionX: " + moveDirectionX);
            }
        }
    }

    private void swapGemsVerticaly(Gem selectedGem, int moveDirectionY) {
        int selectedGemIndex = selectedGem.getIndex();
        int gemToSwapIndex = calculateSwapIndex(moveDirectionY, selectedGemIndex);
        Gem gemToSwap = getGem(gemToSwapIndex);
        if (gemToSwap.isReady()) {
            if (moveDirectionY == 1) {
                disableSwap();
                selectedGem.block();
                gemToSwap.block();
                Gdx.app.debug(TAG, "Swaping " + selectedGem.getType().name() + "(" + selectedGem.position.x + ", " + selectedGem.position.y + ") with " + gemToSwap.getType().name() + "(" + gemToSwap.position.x + ", " + gemToSwap.position.y + ")");
                swapPairArray.add(new SwapPair(selectedGem, gemToSwap, 0, moveDirectionY)); // TODO: prevent new instance, maybe a stack of 5 preinstantioned SwapPair could be created in init
            } else if (moveDirectionY == -1) {
                disableSwap();
                selectedGem.block();
                gemToSwap.block();
                Gdx.app.debug(TAG, "Swaping " + selectedGem.getType().name() + "(" + selectedGem.position.x + ", " + selectedGem.position.y + ") with " + gemToSwap.getType().name() + "(" + gemToSwap.position.x + ", " + gemToSwap.position.y + ")");
                swapPairArray.add(new SwapPair(gemToSwap, selectedGem, 0, moveDirectionY)); // TODO: prevent new instance, maybe a stack of 5 preinstantioned SwapPair could be created in init
            } else {
                throw new RuntimeException("Can not be swapped with moveDirectionY: " + moveDirectionY);
            }
        }
    }

    private int calculateSwapIndex(int moveDirectionY, int selectedGemIndex) {
        return selectedGemIndex + moveDirectionY;
    }

    private void selectGem(int gemIndex) {
        Gem gem = getGem(gemIndex);
        Gdx.app.debug(TAG, "Selecting " + gem.getClass().getSimpleName() + "(" + gem.position.x + ", " + gem.position.y + ")");
        board.selectedGem = gem;
    }

    private Gem getGem(int index) { // TODO: change implementation to avoid interation over all gems
        for (Gem gem : board.gems) {
            if (gem.getIndex() == index) {
                return gem;
            }
        }
        throw new RuntimeException("Gem with index: " + index + "does not exist in the gems array");
    }

    private void clearSelection(String debugInfo) {
        board.selectedGem = null;
    }

    private boolean isVerticalMove(float moveRangeX, float moveRangeY) {
        return moveRangeX == 0 && Math.abs(moveRangeY) == 1;
    }

    private boolean isHorizontalMove(float moveRangeX, float moveRangeY) {
        return Math.abs(moveRangeX) == 1 && moveRangeY == 0;
    }

    private void disableSwap() {
        swapEnabled = false;
    }

    private void enableSwap() {
        swapEnabled = true;
    }

    private boolean isSwapEnabled() {
        return swapEnabled;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        clearSelection("TOUCH_UP");
        enableSwap();
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyUp(int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            reset();
            Gdx.app.debug(TAG, "Game world resetted");
            return true;
        }

        return super.keyUp(keycode);
    }
}
