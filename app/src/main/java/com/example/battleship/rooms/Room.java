package com.example.battleship.rooms;

public class Room {
    String roomName;
    Integer roomID;
    public Room(String roomName,Integer roomID){
        this.roomName=roomName;
        this.roomID=roomID;
    }

    public String getRoomName() {
        return roomName;
    }
}
