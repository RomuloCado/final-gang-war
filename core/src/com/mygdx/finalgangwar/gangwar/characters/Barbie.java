package com.mygdx.finalgangwar.gangwar.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.finalgangwar.gangwar.map.WordBounds;

public class Barbie {
    private static final float SPEED = 5f; // Velocidade de movimento da Barbie

    private Sprite sprite;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> kickAnimation;
    private Animation<TextureRegion> hurtAnimation;
    private Animation<TextureRegion> punchAnimation;
    private float stateTime;

    private int life;
    private boolean isPunching;
    private boolean isKicking;

    private float velocityX;
    private float velocityY;

    public Barbie() {
        sprite = new Sprite(new Texture("barbie_and_punk.png")); // Textura da Barbie
        sprite.setPosition(100, 100); // Posição inicial da Barbie

        // Carregar animações da Barbie a partir de um arquivo .pack
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("barbie_and_punk.pack"));

        Array<TextureRegion> frames = new Array<>();

        frames.addAll(atlas.findRegions("walk"));
        walkAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);

        frames.clear();

        frames.addAll(atlas.findRegions("kick"));
        kickAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);

        frames.clear();

        frames.addAll(atlas.findRegions("hurt"));
        hurtAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);

        frames.clear();

        frames.addAll(atlas.findRegions("punch"));
        punchAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);

        frames.clear();

        stateTime = 0;

        life = 100;
        isPunching = false;
        isKicking = false;

        velocityX = 0;
        velocityY = 0;
    }

    public void update() {
        handleInput();

        // Atualizar a animação da Barbie
        stateTime += Gdx.graphics.getDeltaTime();

        // Atualizar a posição da Barbie com base na velocidade de movimento
        sprite.setX(sprite.getX() + velocityX);
        sprite.setY(sprite.getY() + velocityY);
    }

    public void render(SpriteBatch batch) {
        // Renderizar a animação atual da Barbie
        TextureRegion currentFrame = getCurrentFrame();
        batch.draw(currentFrame, sprite.getX(), sprite.getY());

        // Renderizar outros elementos visuais, como barras de vida, etc.
    }

    private void handleInput() {
        // Lógica de movimento da Barbie
        velocityX = 0;
        velocityY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocityY = SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocityY = -SPEED;
        }

        // Atualizar a posição da Barbie com base na velocidade de movimento
        float newX = sprite.getX() + velocityX;
        float newY = sprite.getY() + velocityY;

        // Verificar se a nova posição está dentro dos limites do mundo
        if (newX >= WordBounds.getWorldBounds().x && newX <= WordBounds.getWorldBounds().x + WordBounds.getWorldBounds().width - sprite.getWidth()) {
            sprite.setX(newX);
        }
        if (newY >= WordBounds.getWorldBounds().y && newY <= WordBounds.getWorldBounds().y + WordBounds.getWorldBounds().height - sprite.getHeight()) {
            sprite.setY(newY);
        }
    }

    private TextureRegion getCurrentFrame() {
        if (isPunching) {
            return punchAnimation.getKeyFrame(stateTime);
        } else if (isKicking) {
            return kickAnimation.getKeyFrame(stateTime);
        } else if (life <= 0) {
            return hurtAnimation.getKeyFrame(stateTime);
        } else if (velocityX != 0 || velocityY != 0) {
            return walkAnimation.getKeyFrame(stateTime);
        } else {
            return walkAnimation.getKeyFrame(0); // Standing animation
        }
    }

    private void punch() {
        isPunching = true;
        // Lógica para executar a animação de soco

        // Reiniciar o tempo da animação
        stateTime = 0;
    }

    private void kick() {
        isKicking = true;
        // Lógica para executar a animação de chute

        // Reiniciar o tempo da animação
        stateTime = 0;
    }

    public float getPositionX() {
        return sprite.getX();
    }

    public float getPositionY() {
        return sprite.getY();
    }

    // Outros métodos, como getters/setters para atributos, lógica de colisões, etc.
}