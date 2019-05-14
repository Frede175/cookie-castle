package dk.sdu.cookie.castle.common.services;

import dk.sdu.cookie.castle.common.util.Vector2f;

import java.util.LinkedList;

public interface AIService {
    LinkedList<Vector2f> calculateRoute(Vector2f start, Vector2f end);
}
