package com.mygdx.finalgangwar.gangwar.map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.finalgangwar.gangwar.characters.Barbie;

public class Map extends ApplicationAdapter {
    private static final int TILE_SIZE = 16; // Tamanho de cada bloco/tile em pixels

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Barbie barbie;
    private static Rectangle worldBounds;

    @Override
    public void create() {
        float mapHeight = 15 * TILE_SIZE; // Altura do mapa em pixels

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float aspectRatio = screenWidth / screenHeight;
        float cameraHeight = mapHeight;
        float cameraWidth = mapHeight * aspectRatio;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, cameraWidth, cameraHeight);
        camera.update();

        batch = new SpriteBatch();

        barbie = new Barbie();

        // Carregando o mapa TiledMap
        TmxMapLoader mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("finalfighter.tmx");

        int tileWidth = (int) tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int) tiledMap.getProperties().get("tileheight");
        int numTilesHorizontal = (int) tiledMap.getProperties().get("width");
        int numTilesVertical = (int) tiledMap.getProperties().get("height");
        float mapWidthInPixels = tileWidth * numTilesHorizontal;
        float mapHeightInPixels = tileHeight * numTilesVertical;
        WordBounds.setWorldBounds(mapWidthInPixels, mapHeightInPixels);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Atualizar a posição da câmera para seguir a personagem
        camera.position.set(barbie.getPositionX(), barbie.getPositionY(), 0);
        camera.update();

        // Renderização do mapa
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        barbie.update();
        barbie.render(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.render();
        batch.dispose();
    }
}