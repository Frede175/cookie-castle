package dk.sdu.cookie.castle.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.osgi.framework.BundleContext;

public class Activator {

    private Game g;
    private LwjglApplication app;

    private void activate(BundleContext context) {
        g = new Game();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Asteroids";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

        LwjglApplication app = new LwjglApplication(g, cfg);
    }

    private void deactivate() {
        app.stop();
    }
}
