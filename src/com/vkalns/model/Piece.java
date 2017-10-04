package com.vkalns.model;

public class Piece
{
    private String colour;
    private String state ="normal";//defines if it's normal or Queen
    private Position position = new Position();
    private String value;

    //constructors
    public Piece(){};

    public Piece(String colour, Position position, String value) {
        this.colour = colour;
        this.position = position;
        this.value = value;
    }


    //getters and setters
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
