package com.mygdx.finalgangwar.mariobros.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.finalgangwar.mariobros.MarioBros;
import com.mygdx.finalgangwar.mariobros.Scenes.Hud;
import com.mygdx.finalgangwar.mariobros.Screens.PlayScreen;
import com.mygdx.finalgangwar.mariobros.Sprites.Mario;

/**
 * Created by brentaureli on 8/28/15.
 */
public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        if(mario.isBig()) {
            setCategoryFilter(MarioBros.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            MarioBros.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
        MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }

}
