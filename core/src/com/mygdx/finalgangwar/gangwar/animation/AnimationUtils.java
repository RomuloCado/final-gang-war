package com.mygdx.finalgangwar.gangwar.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AnimationUtils {
    public static Animation<TextureAtlas.AtlasRegion> loadAnimation(String packFilePath) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(packFilePath));
        Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();

        float frameDuration = 0.2f; // Duração de cada quadro da animação em segundos
        Animation<TextureAtlas.AtlasRegion> animation = new Animation<>(frameDuration, regions);

        return animation;
    }
}
