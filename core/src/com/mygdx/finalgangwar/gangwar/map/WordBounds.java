package com.mygdx.finalgangwar.gangwar.map;

import com.badlogic.gdx.math.Rectangle;

public class WordBounds {

    private static Rectangle worldBounds;

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static Rectangle getWorldBounds(){
        return worldBounds;
    }
}
