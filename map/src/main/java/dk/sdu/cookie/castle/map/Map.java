package dk.sdu.cookie.castle.map;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private static Map map = null;

    private List<Room> listOfRooms;
    private Room currentRoom;

    //Singleton
    public static Map getInstance() {
        if (map == null) {
            map = new Map();
        }
        return map;
    }

    private Map() {
        listOfRooms = new ArrayList<>();
    }




    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public List<Room> getListOfRooms() {
        return listOfRooms;
    }

    public void setListOfRooms(List<Room> listOfRooms) {
        this.listOfRooms = listOfRooms;
    }
}
