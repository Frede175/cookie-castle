package dk.sdu.cookie.castle.common.data;

import dk.sdu.cookie.castle.common.assets.Asset;
import dk.sdu.cookie.castle.common.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private float delta;
    private int displayWidth;
    private int displayHeight;
    private final GameKeys keys = new GameKeys();
    private List<Event> events = new CopyOnWriteArrayList<>();
    private Map<String, Asset> activeAssets = new ConcurrentHashMap<>();

    public void addEvent(Event e) {
        events.add(e);
    }

    public void removeEvent(Event e) {
        events.remove(e);
    }

    public List<Event> getEvents() {
        return events;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setDisplayWidth(int width) {
        displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }

    public Map<String, Asset> getActiveAssets() {
        return activeAssets;
    }

    public void addAsset(Asset asset) {
        activeAssets.put(asset.getId(), asset);
    }

    public void addAssets(Map<String, Asset> assets) {
        activeAssets.putAll(assets);
    }

    public void removeAssets(Map<String, Asset> assets) {
        for (Map.Entry<String, Asset> asset : assets.entrySet()) {
            activeAssets.remove(asset.getKey());
        }
    }
}
