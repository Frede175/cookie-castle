package dk.sdu.cookie.castle.game.managers;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputAdapter;
import dk.sdu.cookie.castle.common.data.GameData;
import dk.sdu.cookie.castle.common.data.GameKeys;
import com.badlogic.gdx.Input.Keys;

public class GameInputProcessor extends InputAdapter {
    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

    public boolean keyDown(int k) {
        handleKeys(k, true);
        return true;
    }

    public boolean keyUp(int k) {
        handleKeys(k, false);
        return true;
    }

    private void handleKeys(int k, boolean isDown) {
        switch (k) {
            case Keys.UP:
                gameData.getKeys().setKey(GameKeys.UP, isDown);
                break;
            case Keys.DOWN:
                gameData.getKeys().setKey(GameKeys.DOWN, isDown);
                break;
            case Keys.LEFT:
                gameData.getKeys().setKey(GameKeys.LEFT, isDown);
                break;
            case Keys.RIGHT:
                gameData.getKeys().setKey(GameKeys.RIGHT, isDown);
                break;
            case Keys.ENTER:
                gameData.getKeys().setKey(GameKeys.ENTER, isDown);
                break;
            case Keys.ESCAPE:
                gameData.getKeys().setKey(GameKeys.ESCAPE, isDown);
                break;
            case Keys.SPACE:
                gameData.getKeys().setKey(GameKeys.SPACE, isDown);
                break;
            case Keys.SHIFT_LEFT:
            case Keys.SHIFT_RIGHT:
                gameData.getKeys().setKey(GameKeys.SHIFT, isDown);
                break;
        }
    }
}





