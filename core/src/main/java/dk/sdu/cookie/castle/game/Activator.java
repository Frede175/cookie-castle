package dk.sdu.cookie.castle.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.osgi.framework.BundleContext;

public class Activator {

    private LwjglApplication app;

    private void activate(BundleContext context) {
        System.out.println("Starting Core!");

        Game game = new Game();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Asteroids";
        cfg.width = game.getDisplayWidth();
        cfg.height = game.getDisplayHeight();
        cfg.useGL30 = false;
        cfg.resizable = false;

        app = new LwjglApplication(game, cfg);
    }

    private void deactivate() {
        app.stop();
    }
}
