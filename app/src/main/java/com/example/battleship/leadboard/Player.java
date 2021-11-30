package com.example.battleship.leadboard;

public class Player {
    private int winvsRobot, winvsUser;
    private String name;
    public Player(String name, int winvsRobot,int winvsUser){
        this.winvsUser = winvsUser;
        this.winvsRobot =winvsRobot;
        this.name=name;
    }



    public String getName() {
        return name;
    }

    public int getWinvsRobot() {
        return winvsRobot;
    }

    public int getWinvsUser() {
        return winvsUser;
    }
}
