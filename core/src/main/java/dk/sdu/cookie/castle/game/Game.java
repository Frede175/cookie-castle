package dk.sdu.cookie.castle.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.cookie.castle.common.data.Entity;
import dk.sdu.cookie.castle.common.data.Entityparts.PositionPart;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.services.IGamePluginService;
import dk.sdu.cookie.castle.common.services.IPostEntityProcessingService;
import dk.sdu.cookie.castle.game.managers.GameInputProcessor;
import dk.sdu.cookie.castle.game.managers.MyAssetManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private static final List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
    private static final List<IPostEntityProcessingService> postProcessingServices = new CopyOnWriteArrayList<>();
    private static final List<IEntityProcessingService> processingServices = new CopyOnWriteArrayList<>();
    private static OrthographicCamera cam;
    private static final GameData gameData = new GameData();
    private static ShapeRenderer sr;
    private static final World world = new World();
    private static SpriteBatch batch;
    private MyAssetManager assetManager;

    public Game() {
    }

    @Override
    public void create() {
        System.out.println("Game created");
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        assetManager = new MyAssetManager();

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        // Ensure that all assets have been loaded before continuing
        if (!assetManager.update(gameData)) {
            return;
        }

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();

        batch.begin();

        // Draw background
        Texture background = assetManager.getBackground();
        batch.draw(background, 0, 0);
        drawEntities();
        batch.end();

        draw();
        update();
    }

    private void drawEntities() {
        for (Entity entity : world.getEntities()) {
            if (!entity.getCurrentTextureId().equals("")) {
                PositionPart position = entity.getPart(PositionPart.class);
                batch.draw(assetManager.get(entity.getCurrentTextureId(), Texture.class), position.getX(), position.getY());
            }
        }
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : processingServices) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postProcessingServices) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapeX = entity.getShapeX();
            float[] shapeY = entity.getShapeY();

            for (int i = 0, j = shapeX.length - 1;
                 i < shapeX.length;
                 j = i++) {

                sr.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
            }

            sr.end();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    protected void installPlugin(IGamePluginService plugin) {
        plugin.start(gameData, world);
        plugins.add(plugin);
    }

    protected void uninstallPlugin(IGamePluginService plugin) {
        plugin.stop(gameData, world);
        plugins.remove(plugin);
    }

    protected void installProcessingService(IEntityProcessingService processingService) {
        processingServices.add(processingService);
    }

    protected void uninstallProcessingService(IEntityProcessingService processingService) {
        processingServices.remove(processingService);
    }

    protected void installPostProcessingService(IPostEntityProcessingService postProcessingService) {
        postProcessingServices.add(postProcessingService);
    }

    protected void uninstallPostProcessingService(IPostEntityProcessingService postProcessingService) {
        postProcessingServices.remove(postProcessingService);
    }
}
