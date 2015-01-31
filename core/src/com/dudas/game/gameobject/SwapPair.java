package com.dudas.game.gameobject;

/**
 * Created by OLO on 25. 1. 2015
 */
public class SwapPair {

    public Gem gem1;
    public Gem gem2;
    public float leftPositionXOrigin;
    public float leftPositionYOrigin;
    public float rightPositionXOrigin;
    public float rightPositionYOrigin;
    public int gem1IndexOrigin;
    public int gem2IndexOrigin;
    public int moveDirectionX;
    public int moveDirectionY;

    public SwapPair(Gem gem1, Gem gem2, int moveDirectionX, int moveDirectionY) {
        this.gem1 = gem1;
        this.gem2 = gem2;
        this.moveDirectionX = moveDirectionX;
        this.moveDirectionY = moveDirectionY;
        this.leftPositionXOrigin = gem1.position.x;
        this.leftPositionYOrigin = gem1.position.y;
        this.rightPositionXOrigin = gem2.position.x;
        this.rightPositionYOrigin = gem2.position.y;
        this.gem1IndexOrigin = gem1.getIndex();
        this.gem2IndexOrigin = gem2.getIndex();
    }
}
