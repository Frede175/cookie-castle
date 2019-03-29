package dk.sdu.cookie.castle.common.services;

import dk.sdu.cookie.castle.common.data.Point;

import java.util.LinkedList;

public interface AIService {
    LinkedList<Point> calculateRoute(Point start, Point end);
}
