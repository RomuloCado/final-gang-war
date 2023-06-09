package com.mygdx.finalgangwar.gangwar.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Barbie {
    private static final float SPEED = 0.7f; // Velocidade de movimento da Barbie

    private static final int FRAME_COLS = 10, FRAME_ROWS = 1;
    private static final int FRAME_COLS_PUNCH = 3, FRAME_ROWS_PUNCH = 1;
    private static final int FRAME_COLS_KICK = 5, FRAME_ROWS_KICK = 1;
    private static final int FRAME_COLS_HURT = 2, FRAME_ROWS_HURT = 1;

    private Sprite sprite;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> kickAnimation;
    private Animation<TextureRegion> hurtAnimation;
    private Animation<TextureRegion> punchAnimation;
    private float stateTime;
    Texture walkSheet;
    Texture kickSheet;
    Texture punchSheet;
    Texture hurtSheet;

    SpriteBatch spriteBatch;

    private int life;
    private boolean isPunching;
    private boolean isKicking;

    private float velocityX;
    private float velocityY;

    private Rectangle mapBounds;

    public Barbie(Rectangle mapBounds) {
        this.mapBounds = mapBounds;
        walkSheet = new Texture(Gdx.files.internal("walk.png"));
        kickSheet = new Texture(Gdx.files.internal("kick.png"));
        punchSheet = new Texture(Gdx.files.internal("punch.png"));
        hurtSheet = new Texture(Gdx.files.internal("hurt.png"));

        sprite = new Sprite(new Texture("barbie_and_punk.png")); // Textura da Barbie

        sprite.setPosition(100, 100); // Posição inicial da Barbie

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        walkAnimation = new Animation<>(0.1f, walkFrames);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[][] tmpKick = TextureRegion.split(kickSheet,
                kickSheet.getWidth() / FRAME_COLS_KICK,
                kickSheet.getHeight() / FRAME_ROWS_KICK);

        TextureRegion[] kikFrames = new TextureRegion[FRAME_COLS_KICK * FRAME_ROWS_KICK];
        int indexKick = 0;
        for (int i = 0; i < FRAME_ROWS_KICK; i++) {
            for (int j = 0; j < FRAME_COLS_KICK; j++) {
                kikFrames[indexKick++] = tmpKick[i][j];
            }
        }

        kickAnimation = new Animation<>(0.1f, kikFrames);
        kickAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        TextureRegion[][] tmpPunch = TextureRegion.split(punchSheet,
                punchSheet.getWidth() / FRAME_COLS_PUNCH,
                punchSheet.getHeight() / FRAME_ROWS_PUNCH);

        TextureRegion[] punchFrames = new TextureRegion[FRAME_COLS_PUNCH * FRAME_ROWS_PUNCH];
        int indexPunch = 0;
        for (int i = 0; i < FRAME_ROWS_PUNCH; i++) {
            for (int j = 0; j < FRAME_COLS_PUNCH; j++) {
                punchFrames[indexPunch++] = tmpPunch[i][j];
            }
        }

        punchAnimation = new Animation<>(0.1f, punchFrames);
        punchAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        TextureRegion[][] tmpHurt = TextureRegion.split(hurtSheet,
                hurtSheet.getWidth() / FRAME_COLS_HURT,
                hurtSheet.getHeight() / FRAME_ROWS_HURT);

        TextureRegion[] hurtFrames = new TextureRegion[FRAME_COLS_HURT * FRAME_ROWS_HURT];
        int indexHurt = 0;
        for (int i = 0; i < FRAME_ROWS_HURT; i++) {
            for (int j = 0; j < FRAME_COLS_HURT; j++) {
                hurtFrames[indexHurt++] = tmpHurt[i][j];
            }
        }

        hurtAnimation = new Animation<>(0.1f, hurtFrames);
        hurtAnimation.setPlayMode(Animation.PlayMode.NORMAL);

        spriteBatch = new SpriteBatch();
        stateTime = 0f;

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

        // Calcula a próxima posição da Barbie com base na velocidade de movimento
        float nextX = sprite.getX() + velocityX;
        float nextY = sprite.getY() + velocityY;

        // Verifica se a próxima posição está dentro dos limites do mapa na dimensão X
        if (nextX >= mapBounds.x && nextX <= mapBounds.x + mapBounds.width) {
            sprite.setX(nextX);
        } else {
            // A próxima posição está fora dos limites do mapa na dimensão X, portanto, não atualizamos a posição X
            velocityX = 0;
        }

        // Verifica se a próxima posição está dentro dos limites do mapa na dimensão Y
        if (nextY >= mapBounds.y && nextY <= mapBounds.y + mapBounds.height) {
            sprite.setY(nextY);
        }

        // Verifica se a próxima posição está dentro dos limites do mapa na dimensão Y superior (limite de cima)
        if (nextY >= mapBounds.y && nextY + sprite.getHeight() <= mapBounds.y + mapBounds.height) {
            sprite.setY(nextY);
        }

        if (isPunching && punchAnimation.isAnimationFinished(stateTime)) {
            isPunching = false;
            stateTime = 0f;
        }

        if (isKicking && kickAnimation.isAnimationFinished(stateTime)) {
            isKicking = false;
            stateTime = 0f;
        }
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

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocityY = SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocityY = -SPEED;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            punch();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            kick();
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
            stateTime = 0f;
            return walkAnimation.getKeyFrame(0);
        }
    }

    private void punch() {
        isPunching = true;
        // Lógica para executar a animação de soco

        // Reiniciar o tempo da animação
        stateTime = 0f;
    }

    private void kick() {
        isKicking = true;
        // Lógica para executar a animação de chute

        // Reiniciar o tempo da animação
        stateTime = 0f;
    }

    public float getPositionX() {
        return sprite.getX();
    }

    public float getPositionY() {
        return sprite.getY();
    }


    // Outros métodos, como getters/setters para atributos, lógica de colisões, etc.
}