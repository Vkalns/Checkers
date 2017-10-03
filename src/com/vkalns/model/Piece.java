package com.vkalns.model;

public class Piece
{
    private String colour;
    private String state ="normal";//defines if it's normal or Queen
    private Position position = new Position();
    private String value;

    public Piece(String colour, Position position, String value) {
        this.colour = colour;
        this.position = position;
        this.value = value;
    }
}
