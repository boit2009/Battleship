package com.example.battleship.grid;

public class Field {
    private State state;
    private int id;
    public Field(int id,State state){
        this.id=id;
        this.state=state;
    }

    public State getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(State state) {
        this.state = state;
    }
}
