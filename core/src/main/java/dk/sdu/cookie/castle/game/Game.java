package dk.sdu.cookie.castle.game;

import com.badlogic.gdx.ApplicationListener;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.World;
import dk.sdu.cookie.castle.common.services.IEntityProcessingService;
import dk.sdu.cookie.castle.common.services.IGamePluginService;
import dk.sdu.cookie.castle.common.services.IPostEntityProcessingService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
    private List<IPostEntityProcessingService> postProcessingServices = new CopyOnWriteArrayList<>();
    private List<IEntityProcessingService> processingServices = new CopyOnWriteArrayList<>();
    private GameData gameData;
    private World world;

    public Game() { }


    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {

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
