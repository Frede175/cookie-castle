package dk.sdu.cookie.castle.common.events;


import dk.sdu.cookie.castle.common.data.Entity;

import java.io.Serializable;

/**
 *
 * @author Mads
 */
public class Event implements Serializable {
    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}
